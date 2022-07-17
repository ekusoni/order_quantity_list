package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import actions.views.CookingView;
import actions.views.MaterialMasterView;
import actions.views.MaterialView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.validators.CookingValidator;
import models.validators.MaterialValidator;
import services.CookingService;
import services.MaterialMasterService;
import services.MaterialService;

/**
 *
 * 料理に関する処理を行うActionクラス
 *
 */
public class CookingAction extends ActionBase {

    private CookingService serviceCoo;
    private MaterialMasterService serviceMas;
    private MaterialService serviceMat;
    private MaterialMasterView mmv;

    @Override
    public void process() throws ServletException, IOException {

        serviceCoo = new CookingService();
        serviceMas = new MaterialMasterService();
        serviceMat = new MaterialService();

        //メソッドを実行
        invoke();
        serviceCoo.close();
        serviceMas.close();
        serviceMat.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する料理データを取得
        int page = getPage();
        List<CookingView> cookings = serviceCoo.getAllPerPage(page);
        List<MaterialView> materials = serviceMat.getPage();
        List<MaterialMasterView> materialMasters = serviceMas.getSelectList();

        //全料理データの件数を取得
        long cookingsCount = serviceCoo.countAll();

        putRequestScope(AttributeConst.COOKINGS, cookings);//取得した料理データ
        putRequestScope(AttributeConst.MATERIALS, materials);//取得した材料データ

        putRequestScope(AttributeConst.COO_COUNT, cookingsCount);//全ての料理データの件数
        putRequestScope(AttributeConst.PAGE, page);//ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);//1ページに表示するレコードの数
        putRequestScope(AttributeConst.MATERIALMS, materialMasters);//

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        if (getSessionScope(AttributeConst.MAT_TENTATIVE) != null) {
            removeSessionScope(AttributeConst.MAT_TENTATIVE);
        }

        List<CookingView> searchCookings = getSessionScope(AttributeConst.SEARCH_COOKINGS);
        if (searchCookings != null) {
            putRequestScope(AttributeConst.SEARCH_COOKINGS, searchCookings);
            removeSessionScope(AttributeConst.SEARCH_COOKINGS);
        }

        List<MaterialView> searchMaterials = getSessionScope(AttributeConst.SEARCH_MATERIALS);
        if (searchMaterials != null) {
            List<CookingView> noDuplicationsCoo = getSessionScope(AttributeConst.NODUPLICATIONS_COOKINGS);
            putRequestScope(AttributeConst.SEARCH_MATERIALS, searchMaterials);
            putRequestScope(AttributeConst.NODUPLICATIONS_COOKINGS, noDuplicationsCoo);
            removeSessionScope(AttributeConst.SEARCH_MATERIALS);
            removeSessionScope(AttributeConst.NODUPLICATIONS_COOKINGS);
        }
        System.out.println("searchCookings" + searchCookings);

        //一覧画面を表示
        forward(ForwardConst.FW_COO_INDEX);

    }

    /**
     * 新規登録画面を表示する
     * @throwsServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        if (checkAuthor()) {

            putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン

            CookingView cv = new CookingView();
            putRequestScope(AttributeConst.COOKING, cv);//空の料理インスタンス
            String error = getSessionScope(AttributeConst.ERR);
            if (getSessionScope(AttributeConst.DURING_REGISTRATION) != null) {
                removeSessionScope(AttributeConst.DURING_REGISTRATION);
            }
            if (error != null) {
                putRequestScope(AttributeConst.ERR, error);
                removeSessionScope(AttributeConst.ERR);
            }

            //新規登録画面を表示
            forward(ForwardConst.FW_COO_NEW);
        }
    }

    /**
     * 材料を選択する画面を表示
     * @throws ServletException
     * @throws IOException
     */
    public void next() throws ServletException, IOException {

        if (checkToken() && checkAuthor()) {

            //料理名が入力されているかの判定
            if (getSessionScope(AttributeConst.DURING_REGISTRATION) == null) {

                CookingView cv = new CookingView(
                        null,
                        getRequestParam(AttributeConst.COO_NAME));
                String error = CookingValidator.validate(serviceCoo, cv, true);

                if (error != "") {
                    putSessionScope(AttributeConst.ERR, error);
                    //新規登録画面を表示
                    redirect(ForwardConst.ACT_COO, ForwardConst.CMD_NEW);
                } else {

                    List<MaterialMasterView> materialMastersSelect = serviceMas.getSelectList();

                    putRequestScope(AttributeConst.MATERIALMSS, materialMastersSelect);
                    putRequestScope(AttributeConst.TOKEN, getTokenId());
                    putSessionScope(AttributeConst.DURING_REGISTRATION, cv);
                    //次の画面を表示
                    forward(ForwardConst.FW_COO_NEXT);
                }
            }

            //材料名の選択画面かの判定(選択画面ならidを入手)
            else if (getRequestParam(AttributeConst.MAT_COL_MATERIALMASTER_ID) != null) {

                mmv = serviceMas
                        .findOne(toNumber(getRequestParam(AttributeConst.MAT_COL_MATERIALMASTER_ID)));

                putSessionScope(AttributeConst.MATERIALM, mmv);//取得した材料名、材料の単位materialMasterViewを格納
                putRequestScope(AttributeConst.TOKEN, getTokenId());

                //次の画面を表示
                forward(ForwardConst.FW_COO_NEXT);

                //量の入力画面かの判定
            } else if (getRequestParam(AttributeConst.MAT_AMOUNT) != null) {
                MaterialMasterView mmv = getSessionScope(AttributeConst.MATERIALM);

                MaterialView mv = new MaterialView(
                        null,
                        mmv,
                        null,
                        toNumber(getRequestParam(AttributeConst.MAT_AMOUNT)));

                String error = MaterialValidator.validate(mv);

                if (error != "") {
                    putRequestScope(AttributeConst.ERR, error);
                    putRequestScope(AttributeConst.TOKEN, getTokenId());
                    //次の画面を表示
                    forward(ForwardConst.FW_COO_NEXT);
                }

                /*
                 * mvArrayにMaterialViewの内容をいれて、mvArrayTにコピーする。追加された分をmyArrayTに代入する。
                 * 上を繰り返し行う。
                 */

                else if (getSessionScope(AttributeConst.MAT_TENTATIVE) == null) {
                    removeSessionScope(AttributeConst.MATERIALM);
                    MaterialView[] mvArray = new MaterialView[1];
                    mvArray[0] = mv;
                    putSessionScope(AttributeConst.MAT_TENTATIVE, mvArray);
                    putRequestScope(AttributeConst.MAT_TENTATIVE_LENGTH, mvArray.length);
                    putRequestScope(AttributeConst.TOKEN, getTokenId());
                    //次の画面を表示
                    forward(ForwardConst.FW_COO_NEXT);

                } else {
                    removeSessionScope(AttributeConst.MATERIALM);
                    MaterialView[] mvArray = getSessionScope(AttributeConst.MAT_TENTATIVE);
                    MaterialView[] mvArrayT = new MaterialView[mvArray.length + 1];
                    System.arraycopy(mvArray, 0, mvArrayT, 0, mvArray.length);
                    mvArrayT[mvArray.length] = mv;
                    putSessionScope(AttributeConst.MAT_TENTATIVE, mvArrayT);
                    putRequestScope(AttributeConst.MAT_TENTATIVE_LENGTH, mvArrayT.length);
                    putRequestScope(AttributeConst.TOKEN, getTokenId());
                    //次の画面を表示
                    forward(ForwardConst.FW_COO_NEXT);

                }
            } else {
                List<MaterialMasterView> materialMastersSelect = serviceMas.getSelectList();
                putRequestScope(AttributeConst.MATERIALMSS, materialMastersSelect);
                putRequestScope(AttributeConst.TOKEN, getTokenId());
                //次の画面を表示
                forward(ForwardConst.FW_COO_NEXT);

            }
        }
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        if (checkAuthor()) {

            MaterialView[] mvArrayT = getSessionScope(AttributeConst.MAT_TENTATIVE);
            CookingView cv = getSessionScope(AttributeConst.DURING_REGISTRATION);

            serviceCoo.create(getSessionScope(AttributeConst.DURING_REGISTRATION));
            CookingView searchCv = serviceCoo.findName(cv.getName());

            for (int i = 0; i < mvArrayT.length; i++) {
                mvArrayT[i].setCooking(searchCv);

                serviceMat.create(mvArrayT[i]);
            }

            removeSessionScope(AttributeConst.DURING_REGISTRATION);
            removeSessionScope(AttributeConst.MAT_TENTATIVE);
            putSessionScope(AttributeConst.FLUSH,MessageConst.I_REGISTERED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_COO, ForwardConst.CMD_INDEX);
        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に料理データを取得する
        CookingView cv = serviceCoo.findOne(toNumber(getRequestParam(AttributeConst.COO_ID)));
        List<MaterialView> materials = serviceMat.getPage();

        if (cv == null) {
            //該当の料理データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
        } else {
            putRequestScope(AttributeConst.COOKING, cv);//取得した料理データ
            putRequestScope(AttributeConst.MATERIALS, materials);//取得した材料データ

            //詳細画面を表示
            forward(ForwardConst.FW_COO_SHOW);
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        if (checkAuthor()) {

            //idを条件に料理データを取得する
            CookingView cv = serviceCoo.findOne(toNumber(getRequestParam(AttributeConst.COO_ID)));
            List<MaterialView> materials = serviceMat.getPage();

            if (cv == null) {

                //該当の料理データが存在しない場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
            } else {

                putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
                putRequestScope(AttributeConst.COOKING, cv);//取得した料理データ
                putRequestScope(AttributeConst.MATERIALS, materials);//取得した材料データ
                //編集画面を表示
                forward(ForwardConst.FW_COO_EDIT);

            }
        }
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策tokenのチェック
        if (checkAuthor() && checkToken()) {
            int i = 0;
            List<String> errors = new ArrayList<>();

            //idを条件に料理データを取得する
            CookingView cv = serviceCoo.findOne(toNumber(getRequestParam(AttributeConst.COO_ID)));
            List<MaterialView> materials = serviceMat.getPage();

            String[] amount = getRequestParamValues(AttributeConst.MAT_AMOUNT);

            boolean validateName = false;
            if (!cv.getName().equals(getRequestParam(AttributeConst.COO_NAME))) {
                //料理名を更新する場合

                //料理名についてのバリデーションを行う
                validateName = true;
                //変更後の料理名を設定する
                cv.setName(getRequestParam(AttributeConst.COO_NAME));
            }
            String errorCoo = CookingValidator.validate(serviceCoo, cv, validateName);
            if (errorCoo != "") {
                errors.add(errorCoo);
            }
            String errorMat = "";
            MaterialView mvs[] = new MaterialView[amount.length];

            for (MaterialView material : materials) {
                if (cv.getId() == material.getCooking().getId()) {
                    //材料データの中の料理データidと料理データidが同じ場合

                    //変更後の量を設定する
                    material.setAmount(toNumber(amount[i]));

                    if (material.getAmount() < 0) {
                        material.setAmount(0);
                    }
                    mvs[i] = material;
                    if (errorMat == "") {
                        errorMat = MaterialValidator.validate(material);
                    }
                    i++;

                }
            }
            if (errorMat != "") {
                errors.add(errorMat);
            }

            if (errors.size() != 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.ERR, errors);
                putRequestScope(AttributeConst.TOKEN, getTokenId());
                putRequestScope(AttributeConst.COOKING, cv);
                putRequestScope(AttributeConst.MATERIALS, mvs);

                forward(ForwardConst.FW_COO_EDIT);
            } else {
                //更新中にエラーが無かった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());
                System.out.println("flush" + getSessionScope(AttributeConst.FLUSH));
                serviceCoo.update(cv);
                for (MaterialView mv : mvs) {
                    serviceMat.update(mv);
                }

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_COO, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * 材料を選択する画面を表示
     * @throws ServletException
     * @throws IOException
     */
    public void addition() throws ServletException, IOException {

        if (checkAuthor()) {

            if (getSessionScope(AttributeConst.MAT_TENTATIVE) != null) {
                removeSessionScope(AttributeConst.MAT_TENTATIVE);
            }
            CookingView cv = serviceCoo.findOne(toNumber(getRequestParam(AttributeConst.COO_ID)));
            List<MaterialView> materials = serviceMat.getPage();
            List<MaterialMasterView> materialMastersSelect = serviceMas.getSelectList();

            if (cv == null) {
                //該当の料理データが存在しない場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);

            } else {


                putSessionScope(AttributeConst.COOKING, cv);//取得した料理データ
                putSessionScope(AttributeConst.MATERIALS, materials);
                putRequestScope(AttributeConst.MATERIALMSS, materialMastersSelect);
                putRequestScope(AttributeConst.TOKEN,getTokenId());

                //追加画面を表示
                forward(ForwardConst.FW_COO_ADD);
            }

        }
    }

    /**
     * 材料の量を入力する画面を表示
     * @throws ServletException
     * @throws IOException
     */
    public void increase() throws ServletException, IOException {

        if (checkToken() && checkAuthor()) {

            //材料名の選択画面かの判定(選択画面ならidを入手)
            if (getRequestParam(AttributeConst.MAT_COL_MATERIALMASTER_ID) != null) {
                mmv = serviceMas
                        .findOne(toNumber(getRequestParam(AttributeConst.MAT_COL_MATERIALMASTER_ID)));

                putSessionScope(AttributeConst.MATERIALM, mmv);//取得した材料名、材料の単位materialMasterViewを格納

                //量の入力画面かの判定
            } else if (getRequestParam(AttributeConst.MAT_AMOUNT) != null) {
                MaterialMasterView mmv = getSessionScope(AttributeConst.MATERIALM);

                MaterialView mv = new MaterialView(
                        null,
                        mmv,
                        null,
                        toNumber(getRequestParam(AttributeConst.MAT_AMOUNT)));

                String error = MaterialValidator.validate(mv);

                if (error != "") {

                    putRequestScope(AttributeConst.ERR, error);
                }

                else if (getSessionScope(AttributeConst.MAT_TENTATIVE) == null) {
                    removeSessionScope(AttributeConst.MATERIALM);
                    MaterialView[] mvArray = new MaterialView[1];
                    mvArray[0] = mv;
                    putSessionScope(AttributeConst.MAT_TENTATIVE, mvArray);
                    putRequestScope(AttributeConst.MAT_TENTATIVE_LENGTH, mvArray.length);

                } else {
                    removeSessionScope(AttributeConst.MATERIALM);
                    MaterialView[] mvArray = getSessionScope(AttributeConst.MAT_TENTATIVE);
                    MaterialView[] mvArrayT = new MaterialView[mvArray.length + 1];
                    System.arraycopy(mvArray, 0, mvArrayT, 0, mvArray.length);
                    mvArrayT[mvArray.length] = mv;
                    putSessionScope(AttributeConst.MAT_TENTATIVE, mvArrayT);
                    putRequestScope(AttributeConst.MAT_TENTATIVE_LENGTH, mvArrayT.length);

                }
            } else {
                List<MaterialMasterView> materialMastersSelect = serviceMas.getSelectList();
                putRequestScope(AttributeConst.MATERIALMSS, materialMastersSelect);

            }
            //次の画面を表示
            putRequestScope(AttributeConst.TOKEN,getTokenId());
            forward(ForwardConst.FW_COO_ADD);

        }
    }

    /**
     * 材料の追加を行う
     * @throws ServletException
     * @throws IOException
     */
    public void gain() throws ServletException, IOException {

        if (checkAuthor()) {

            MaterialView[] mvArrayT = getSessionScope(AttributeConst.MAT_TENTATIVE);
            CookingView cv = getSessionScope(AttributeConst.COOKING);

            for (int i = 0; i < mvArrayT.length; i++) {
                mvArrayT[i].setCooking(cv);

                serviceMat.create(mvArrayT[i]);
            }

            removeSessionScope(AttributeConst.COOKING);
            removeSessionScope(AttributeConst.MAT_TENTATIVE);

            //セッションに登録完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_COO, ForwardConst.CMD_INDEX);

        }
    }

    /**
     * 材料の削除する
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        if (checkAuthor()) {

            MaterialView mv = serviceMat.findOne(toNumber(getRequestParam(AttributeConst.MAT_ID)));

            serviceMat.destroy(mv);

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());
            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_COO, ForwardConst.CMD_INDEX);
        }
    }


    /**
     * 検索を行う
     * @throws ServletException
     * @throws IOException
     */
    public void search() throws ServletException, IOException {

        if (getRequestParam(AttributeConst.COOKING).equals("cookingName")) {
            String name = getRequestParam(AttributeConst.COO_WORD);
            if (name != "") {
                List<CookingView> searchCookings = serviceCoo.searchName(name);
                putSessionScope(AttributeConst.SEARCH_COOKINGS, searchCookings);
            }
        } else if (getRequestParam(AttributeConst.COOKING).equals("materialName")) {
            String name = getRequestParam(AttributeConst.COO_WORD);
            if (name != "") {
                List<MaterialView> searchMaterials = serviceMat.searcMatMName(name);
                List<CookingView> cookings = new ArrayList<>();
                List<Integer> cookingIds = new ArrayList<>();

                //検索結果のsearchMaterialsの被りを失くす
                for (MaterialView searchMaterial : searchMaterials) {
                    cookingIds.add(searchMaterial.getCooking().getId());
                }
                List<Integer> cookingNoDuplications = cookingIds.stream().distinct().collect(Collectors.toList());
                for (Integer cookingNoDuplication : cookingNoDuplications) {
                    CookingView cooking = serviceCoo.findOne(cookingNoDuplication);
                    cookings.add(cooking);
                }

                putSessionScope(AttributeConst.NODUPLICATIONS_COOKINGS, cookings);
                putSessionScope(AttributeConst.SEARCH_MATERIALS, searchMaterials);
            }

        }

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_COO, ForwardConst.CMD_INDEX);
    }

}
