package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.MaterialMasterView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.MaterialMasterService;

/**
 * 材料に関わる処理を行うActionクラス
 *
 */
public class MaterialMasterAction extends ActionBase {

    /**
     * メソッドを実行する
     */
    private MaterialMasterService service;

    @Override
    public void process() throws ServletException, IOException {
        service = new MaterialMasterService();

        //メソッドを実行
        invoke();

        service.close();

    }

    /**
     * 一覧画面を表示する
     * @throws ServeletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<MaterialMasterView> materials = service.getPerPage(page);

        //全ての材料データの件数を取得
        long materialMastersCount = service.countAll();

        putRequestScope(AttributeConst.MATERIALMS, materials);//取得した材料データ
        putRequestScope(AttributeConst.MATM_COUNT, materialMastersCount);//全ての材料データの件数
        putRequestScope(AttributeConst.PAGE, page);//ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);//1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替えセッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //セッションに検索結果が設定されている場合はリクエストスコープに移し替えセッションから削除する
        List<MaterialMasterView> searchMaterialMasters = getSessionScope(AttributeConst.SEARCHMATERIALMS);
        if (searchMaterialMasters != null) {
            putRequestScope(AttributeConst.SEARCHMATERIALMS, searchMaterialMasters);
            removeSessionScope(AttributeConst.SEARCHMATERIALMS);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_MATM_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        //作成者かどうかのチェック
        if (checkAuthor()) {
            putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
            putRequestScope(AttributeConst.MATERIALM, new MaterialMasterView());//空の材料インスタンス

            //新規登録画面を表示
            forward(ForwardConst.FW_MATM_NEW);
        }
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策tokenのチェック
        if (checkAuthor() && checkToken()) {

            //パラメータの値を元に材料情報のインスタンスを作成する
            MaterialMasterView mmv = new MaterialMasterView(
                    null,
                    getRequestParam(AttributeConst.MATM_NAME),
                    getRequestParam(AttributeConst.MATM_UNIT));

            //材料情報登録
            List<String> errors = service.create(mmv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合
                putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
                putRequestScope(AttributeConst.MATERIALM, mmv);//入力された材料情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_MATM_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putRequestScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MATM, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * 詳細を見る
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に材料データを取得する
        MaterialMasterView mmv = service.findOne(toNumber(getRequestParam(AttributeConst.MATM_ID)));

        if (mmv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

            return;
        }

        putRequestScope(AttributeConst.MATERIALM, mmv);

        //詳細画面を表示
        forward(ForwardConst.FW_MATM_SHOW);

    }

    public void edit() throws ServletException, IOException {

        if (checkAuthor()) {
            //idを条件に材料データを取得する
            MaterialMasterView mmv = service.findOne(toNumber(getRequestParam(AttributeConst.MATM_ID)));

            if (mmv == null) {
                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
            putRequestScope(AttributeConst.MATERIALM, mmv);//取得した材料情報

            //編集画面を表示する
            forward(ForwardConst.FW_MATM_EDIT);
        }

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        if (checkAuthor() && checkToken()) {

            //パラメータの値を元に材料情報のインスタンスを作成する
            MaterialMasterView mmv = new MaterialMasterView(
                    toNumber(getRequestParam(AttributeConst.MATM_ID)),
                    getRequestParam(AttributeConst.MATM_NAME),
                    getRequestParam(AttributeConst.MATM_UNIT));

            //材料情報更新
            List<String> errors = service.update(mmv);

            if (errors.size() > 0) {

                //更新中にエラーが発生した場合
                putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
                putRequestScope(AttributeConst.MATERIALM, mmv);//入力された材料情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_MATM_EDIT);

            } else {
                //更新中にエラーが無かった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MATM, ForwardConst.CMD_INDEX);
            }

        }

    }

    /**
     * 検索を行う
     * @throws ServletException
     * @throws IOException
     */
    public void search() throws ServletException, IOException {

        //MATERIALMの中身によって処理を分ける
        if (getRequestParam(AttributeConst.MATERIALM).equals("name")) {
            String name = getRequestParam(AttributeConst.MATM_WORD);
            if (name != "") {
                List<MaterialMasterView> searchMaterialMasters = service.searchName(name);
                putSessionScope(AttributeConst.SEARCHMATERIALMS, searchMaterialMasters);
            }
        } else if (getRequestParam(AttributeConst.MATERIALM).equals("unit")) {
            String unit = getRequestParam(AttributeConst.MATM_WORD);
            if (unit != "") {
                List<MaterialMasterView> searchMaterialMasters = service.searchUnit(unit);
                putSessionScope(AttributeConst.SEARCHMATERIALMS, searchMaterialMasters);

            }

        }

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_MATM, ForwardConst.CMD_INDEX);
    }

}
