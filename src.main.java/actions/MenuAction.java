package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.CookingSlaveView;
import actions.views.CookingView;
import actions.views.MenuView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.validators.CookingSlaveValidator;
import models.validators.MenuValidator;
import services.CookingService;
import services.CookingSlaveService;
import services.MenuService;

/**
 * メニューに関わる処理を行うActionクラス
 *
 */
public class MenuAction extends ActionBase {

    private MenuService serviceMen;
    private CookingService serviceCoo;
    private CookingSlaveService serviceCos;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        serviceMen = new MenuService();
        serviceCoo = new CookingService();
        serviceCos = new CookingSlaveService();

        //メソッドを実行
        invoke();

        serviceMen.close();
        serviceCoo.close();
        serviceCos.close();

    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<MenuView> menus = serviceMen.getPerPage(page);

        //全てのメニューデータの件数を取得
        long menuCount = serviceMen.countAll();

        List<CookingSlaveView> cookingSlaves = serviceCos.getPage();
        List<CookingView> cookings = serviceCoo.getSelectList();

        putRequestScope(AttributeConst.MENUS, menus);
        putRequestScope(AttributeConst.MEN_COUNT, menuCount);
        putRequestScope(AttributeConst.COOKINGSLS, cookingSlaves);
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);
        putRequestScope(AttributeConst.COOKINGS, cookings);

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションから削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }
        //一覧画面を表示
        forward(ForwardConst.FW_MEN_INDEX);

    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
        putRequestScope(AttributeConst.MENU, new MenuView());//空のメニューインスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_MEN_NEW);
    }

    /**
     * 次の画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void next() throws ServletException, IOException {

        if (checkToken() && checkAuthor()) {
            List<String> errors = new ArrayList<String>();
            MenuView mv = new MenuView();
            List<CookingView> cookings = serviceCoo.getSelectList();//料理の選択リスト

            LocalDate start = null;//開始日
            LocalDate end = null;//終了日
            //開始日の入力値があるかをチェックし、入力値がなければエラーメッセージを返却
            if (getRequestParam(AttributeConst.MENU_DATE_START) == null
                    || getRequestParam(AttributeConst.MENU_DATE_START).equals("")) {
                errors.add(MessageConst.E_MENU_NOSTART.getMessage());
                mv.setStartDate(null);
            } else {
                start = LocalDate.parse(getRequestParam(AttributeConst.MENU_DATE_START));
                mv.setStartDate(start);

            }

            //終了日の入力値があるかをチェックし、入力値がなければエラーメッセージを返却
            if (getRequestParam(AttributeConst.MENU_DATE_END) == null
                    || getRequestParam(AttributeConst.MENU_DATE_END).equals("")) {
                errors.add(MessageConst.E_MENU_NOEND.getMessage());
                mv.setEndDate(null);
            } else {
                end = LocalDate.parse(getRequestParam(AttributeConst.MENU_DATE_END));
                mv.setEndDate(end);
            }
            mv.setId(null);
            mv.setTopDisplay("no");

            if (mv.getStartDate() != null && mv.getEndDate() != null) {
                errors = MenuValidator.validate(mv);

            }

            //朝、昼、夕のメニューの量取得する
            int morningAmount = toNumber(getRequestParam(AttributeConst.MENU_MORNING_AMOUNT));
            int noonAmount = toNumber(getRequestParam(AttributeConst.MENU_NOON_AMOUNT));
            int eveningAmount = toNumber(getRequestParam(AttributeConst.MENU_EVENING_AMOUNT));

            if (morningAmount < 0) {
                errors.add(MessageConst.E_MENU_NOMORNING.getMessage());
            } else {
                putRequestScope(AttributeConst.MENU_MORNING_AMOUNT, morningAmount);
            }
            if (noonAmount < 0) {
                errors.add(MessageConst.E_MENU_NONOON.getMessage());
            } else {
                putRequestScope(AttributeConst.MENU_NOON_AMOUNT, noonAmount);
            }
            if (eveningAmount < 0) {
                errors.add(MessageConst.E_MENU_EVENING.getMessage());
            } else {
                putRequestScope(AttributeConst.MENU_EVENING_AMOUNT, eveningAmount);
            }

            if (errors.size() > 0) {

                putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
                putRequestScope(AttributeConst.MENU, mv);//入力されたメニュー情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_MEN_NEW);
            } else {

                putRequestScope(AttributeConst.TOKEN, getTokenId());
                putSessionScope(AttributeConst.MENU, mv);
                putRequestScope(AttributeConst.COOKINGS, cookings);

                //次の画面を表示
                forward(ForwardConst.FW_MEN_NEXT);

            }

        }

    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {
        if (checkToken() && checkAuthor()) {

            String[] amount = getRequestParamValues(AttributeConst.COOKINGSL_AMOUNT);//複数の量のデータを取得する
            String[] morningCookingId = getRequestParamValues(AttributeConst.COS_COL_MORNING_COOKING_ID);//朝の料理のidを複数取得する
            String[] noonCookingId = getRequestParamValues(AttributeConst.COS_COL_NOON_COOKING_ID);//昼の料理のidを複数取得する
            String[] eveningCookingId = getRequestParamValues(AttributeConst.COS_COL_EVENING_COOKING_ID);////夕の料理のidを複数取得する
            CookingSlaveView[] csv = new CookingSlaveView[amount.length];
            MenuView mv = (MenuView) getSessionScope(AttributeConst.MENU);
            String error = null;

            //料理(量)データを格納するため配列の長さを取得している
            int morningLength;
            int noonLength;
            int eveningLength;

            if (morningCookingId == null) {
                morningLength = 0;
            } else {
                morningLength = morningCookingId.length;
            }

            if (noonCookingId == null) {
                noonLength = 0;
            } else {
                noonLength = noonCookingId.length;
            }

            if (eveningCookingId == null) {
                eveningLength = 0;
            } else {
                eveningLength = eveningCookingId.length;
            }

            for (int i = 0; i < amount.length; i++) {
                if (i < morningLength) {
                    csv[i] = new CookingSlaveView(
                            null,
                            null,
                            (CookingView) serviceCoo.findOne(toNumber(morningCookingId[i])),
                            toNumber(amount[i]),
                            "morning");
                } else if (i < morningLength + noonLength) {
                    csv[i] = new CookingSlaveView(
                            null,
                            null,
                            (CookingView) serviceCoo.findOne(toNumber(noonCookingId[i - (morningLength)])),
                            toNumber(amount[i]),
                            "noon");
                } else {
                    csv[i] = new CookingSlaveView(
                            null,
                            null,
                            (CookingView) serviceCoo
                                    .findOne(toNumber(eveningCookingId[i - (morningLength + noonLength)])),
                            toNumber(amount[i]),
                            "evening");
                }
            }

            //入力値が無い、又は数字以外の場合はエラーメッセージが格納される
            for (int i = 0; i < amount.length; i++) {
                error = CookingSlaveValidator.validate(csv[i]);
                if (error != null) {
                    break;
                }
            }

            if (error != null) {
                List<CookingView> cookings = serviceCoo.getSelectList();
                putRequestScope(AttributeConst.COOKINGS, cookings);
                putRequestScope(AttributeConst.TOKEN, getTokenId());
                putRequestScope(AttributeConst.MENU_MORNING_AMOUNT, morningLength);
                putRequestScope(AttributeConst.MENU_NOON_AMOUNT, noonLength);
                putRequestScope(AttributeConst.MENU_EVENING_AMOUNT, eveningLength);
                putRequestScope(AttributeConst.COOKINGSLS, csv);
                putRequestScope(AttributeConst.ERR, error);

                forward(ForwardConst.FW_MEN_NEXT);

            } else {

                serviceMen.create(mv);
                mv = serviceMen.findMaxId(); //idが一番大きい数を取得する
                for (int i = 0; i < amount.length; i++) {
                    csv[i].setMenu(mv);
                    serviceCos.create(csv[i]);
                }

                removeSessionScope(AttributeConst.MENU);
                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MENU, ForwardConst.CMD_INDEX);

            }

        }

    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {
        //idを条件にメニューデータを取得する
        MenuView mv = serviceMen.findOne(toNumber(getRequestParam(AttributeConst.MEN_ID)));
        List<CookingSlaveView> cookingSlaves = serviceCos.getPage();

        if (mv == null) {
            //該当のメニューデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
        } else {

            putRequestScope(AttributeConst.MENU, mv);//取得したメニューデータ
            putRequestScope(AttributeConst.COOKINGSLS, cookingSlaves);//取得した料理(量)データ

            //詳細画面を表示
            forward(ForwardConst.FW_MEN_SHOW);
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        if (checkAuthor()) {

            //idを条件にメニューデータを取得する
            MenuView mv = serviceMen.findOne(toNumber(getRequestParam(AttributeConst.MEN_ID)));
            List<CookingSlaveView> cookingSlaves = serviceCos
                    .getMenus(toNumber(getRequestParam(AttributeConst.MEN_ID)));//メニューidを条件に料理(量)データを取得する
            List<CookingView> cookings = serviceCoo.getSelectList();//料理データを全て取得する

            if (mv == null) {
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            } else {

                putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
                putRequestScope(AttributeConst.COOKINGSLS, cookingSlaves);
                putRequestScope(AttributeConst.COOKINGS, cookings);
                putRequestScope(AttributeConst.MENU, mv);

                //編集画面を表示する
                forward(ForwardConst.FW_MEN_EDIT);
            }

        }
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策用tokenのチェック
        if (checkToken() && checkAuthor()) {

            //idを条件に料理(量)データリストを取得する
            MenuView mv = serviceMen.findOne(toNumber(getRequestParam(AttributeConst.MEN_ID)));
            List<CookingSlaveView> cookingSlaves = serviceCos
                    .getMenus(toNumber(getRequestParam(AttributeConst.MEN_ID)));

            //画面入力された料理idを複数取得する
            String[] morningCookingId = getRequestParamValues(AttributeConst.COS_COL_MORNING_COOKING_ID);
            String[] noonCookingId = getRequestParamValues(AttributeConst.COS_COL_NOON_COOKING_ID);
            String[] eveningCookingId = getRequestParamValues(AttributeConst.COS_COL_EVENING_COOKING_ID);
            String[] amount = getRequestParamValues(AttributeConst.COOKINGSL_AMOUNT);
            String error = null;

            //料理(量)データを格納するため配列の長さを取得している
            int morningLength;
            int noonLength;
            int eveningLength;
            int i = 0;

            if (morningCookingId == null) {
                morningLength = 0;

            } else {
                morningLength = morningCookingId.length;
            }

            if (noonCookingId == null) {
                noonLength = 0;
            } else {
                noonLength = noonCookingId.length;
            }

            if (eveningCookingId == null) {
                eveningLength = 0;
            } else {
                eveningLength = eveningCookingId.length;
            }

            //入力された料理(量)内容を設定する
            for (CookingSlaveView cookingSlave : cookingSlaves) {
                if (i < morningLength) {
                    CookingView cv = serviceCoo.findOne(toNumber(morningCookingId[i]));
                    cookingSlave.setCooking(cv);
                    cookingSlave.setAmount(toNumber(amount[i]));
                    if (cookingSlave.getAmount() < 0) {
                        cookingSlave.setAmount(0);
                    }
                } else if (i < morningLength + noonLength) {
                    CookingView cv = serviceCoo.findOne(toNumber(noonCookingId[i - (morningLength)]));
                    cookingSlave.setCooking(cv);
                    cookingSlave.setAmount(toNumber(amount[i]));
                    if (cookingSlave.getAmount() < 0) {
                        cookingSlave.setAmount(0);
                    }
                } else {
                    CookingView cv = serviceCoo.findOne(toNumber(eveningCookingId[i - (morningLength + noonLength)]));
                    cookingSlave.setCooking(cv);
                    cookingSlave.setAmount(toNumber(amount[i]));
                    if (cookingSlave.getAmount() < 0) {
                        cookingSlave.setAmount(0);
                    }
                }

                i++;
            }

            //入力値が無い、又は数字以外の場合はエラーメッセージが格納される
            for (CookingSlaveView cookingSlave : cookingSlaves) {
                error = CookingSlaveValidator.validate(cookingSlave);
                if (error != null) {
                    break;
                }
            }

            if (error != null) {
                List<CookingView> cookings = serviceCoo.getSelectList();
                putRequestScope(AttributeConst.MENU, mv);
                putRequestScope(AttributeConst.COOKINGS, cookings);
                putRequestScope(AttributeConst.TOKEN, getTokenId());
                putRequestScope(AttributeConst.MENU_MORNING_AMOUNT, morningLength);
                putRequestScope(AttributeConst.MENU_NOON_AMOUNT, noonLength);
                putRequestScope(AttributeConst.MENU_EVENING_AMOUNT, eveningLength);
                putRequestScope(AttributeConst.COOKINGSLS, cookingSlaves);
                putRequestScope(AttributeConst.ERR, error);

                forward(ForwardConst.FW_MEN_EDIT);

            } else {
                for (CookingSlaveView cookingSlave : cookingSlaves) {
                    serviceCos.update(cookingSlave);
                }

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MENU, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 追加画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void addition() throws ServletException, IOException {

        if (checkAuthor()) {

            //セッションにlist型の料理データが格納されている場合は消去する
            if (getSessionScope(AttributeConst.COS_TENTATIVE) != null) {
                removeSessionScope(AttributeConst.COS_TENTATIVE);
            }

            //idを条件にメニューデータを取得する
            MenuView mv = serviceMen.findOne(toNumber(getRequestParam(AttributeConst.MEN_ID)));

            //メニューidを条件に複数の料理(量)データを取得する
            List<CookingSlaveView> cookingSlaves = serviceCos
                    .getMenus(toNumber(getRequestParam(AttributeConst.MEN_ID)));

            //料理データを全て取得する
            List<CookingView> cookings = serviceCoo.getSelectList();

            if (mv == null) {
                //該当のメニューデータが存在しない場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);

            } else {
                putRequestScope(AttributeConst.TOKEN, getTokenId());
                putRequestScope(AttributeConst.MENU, mv);
                putRequestScope(AttributeConst.COOKINGSLS, cookingSlaves);
                putRequestScope(AttributeConst.COOKINGS, cookings);

                //追加画面を表示
                forward(ForwardConst.FW_MEN_ADD);
            }
        }

    }

    /**
     * 追加画面と入力情報の表示を繰り返す
     * @throws ServletException
     * @throws IOException
     */
    public void increase() throws ServletException, IOException {
        if (checkToken() && checkAuthor()) {

            List<CookingView> cookings = serviceCoo.getSelectList();
            List<CookingSlaveView> cookingSlaves = serviceCos
                    .getMenus(toNumber(getRequestParam(AttributeConst.MEN_ID)));
            MenuView mv = serviceMen.findOne(toNumber(getRequestParam(AttributeConst.MEN_ID)));
            CookingView cv = new CookingView();
            if (getRequestParam(AttributeConst.COS_COL_COOKING_ID) != null) {

                //料理の選択があった場合
                cv = serviceCoo.findOne(toNumber(getRequestParam(AttributeConst.COS_COL_COOKING_ID)));
                CookingSlaveView csv = new CookingSlaveView(
                        null,
                        mv,
                        cv,
                        toNumber(getRequestParam(AttributeConst.COOKINGSL_AMOUNT)),
                        getRequestParam(AttributeConst.COOKINGSL_TIMEZONE));

                //個数の判定を行う
                String error = CookingSlaveValidator.validate(csv);

                if (error != null) {
                    csv.setAmount(0);

                    putRequestScope(AttributeConst.TOKEN, getTokenId());
                    putRequestScope(AttributeConst.ERR, error);
                    putRequestScope(AttributeConst.COOKINGSL, csv);
                    putRequestScope(AttributeConst.MENU, mv);
                    putRequestScope(AttributeConst.COOKINGS, cookings);

                    //配列に料理(量)の内容を格納して、セッションに保存する
                } else if (getSessionScope(AttributeConst.COS_TENTATIVE) == null) {
                    CookingSlaveView[] csvArray = new CookingSlaveView[1];
                    csvArray[0] = csv;
                    putSessionScope(AttributeConst.COS_TENTATIVE, csvArray);
                    putRequestScope(AttributeConst.COS_TENTATIVE_LENGTH, csvArray.length);
                    System.out.println("csvArrayT" + csvArray[0].getAmount());
                } else {
                    CookingSlaveView[] csvArray = getSessionScope(AttributeConst.COS_TENTATIVE);
                    CookingSlaveView[] csvArrayT = new CookingSlaveView[csvArray.length + 1];
                    System.arraycopy(csvArray, 0, csvArrayT, 0, csvArray.length);
                    csvArrayT[csvArray.length] = csv;
                    putSessionScope(AttributeConst.COS_TENTATIVE, csvArrayT);
                    putRequestScope(AttributeConst.COS_TENTATIVE_LENGTH, csvArrayT.length);

                }

            } else {
                putRequestScope(AttributeConst.COOKINGS, cookings);
                putRequestScope(AttributeConst.COOKINGSL, new CookingSlaveView());//空の料理(量)インスタンス

            }

            putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
            putRequestScope(AttributeConst.MENU, mv);//取得したメニューデータ
            putRequestScope(AttributeConst.COOKINGSLS, cookingSlaves);//取得した料理(量)データ

            //次の画面を表示
            forward(ForwardConst.FW_MEN_ADD);

        }

    }

    /**
     * 料理の追加を行う
     * @throws ServletException
     * @throws IOException
     */
    public void gain() throws ServletException, IOException {

        if (checkAuthor()) {

            //セッションに格納されている料理(量)のデータ取得する
            CookingSlaveView[] csvArrayT = getSessionScope(AttributeConst.COS_TENTATIVE);

            for (int i = 0; i < csvArrayT.length; i++) {
                serviceCos.create(csvArrayT[i]);//料理(量)情報登録
            }

            removeSessionScope(AttributeConst.COS_TENTATIVE);

            //セッションに登録完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());
            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_MENU, ForwardConst.CMD_INDEX);
        }
    }

    /**
     * メニューから料理を削除する
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        if (checkAuthor()) {

            CookingSlaveView csv = serviceCos.findOne(toNumber(getRequestParam(AttributeConst.COS_ID)));

            //料理(量)情報削除
            serviceCos.destroy(csv);

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());
            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_MENU, ForwardConst.CMD_INDEX);

        }
    }

    /**
     * メニューをトップ画面に登録
     * @throws ServletException
     * @throws IOException
     */
    public void top() throws ServletException, IOException {

        if (checkAuthor()) {

            MenuView mv = serviceMen.findOne(toNumber(getRequestParam(AttributeConst.MEN_ID)));

            if (mv == null) {
                //該当のメニューデータが存在しない場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
            } else {
                List<MenuView> menus = serviceMen.getPage();
                long one = serviceMen.countByTopDisplay("one");
                long two = serviceMen.countByTopDisplay("two");

                if (one == 0 && two == 0) {
                    mv.setTopDisplay("one");
                } else if (two == 0) {
                    mv.setTopDisplay("two");
                } else {
                    for (MenuView menu : menus) {
                        if (menu.getTopDisplay().equals("two")) {
                            menu.setTopDisplay("one");
                            serviceMen.update(menu);
                        } else if (menu.getTopDisplay().equals("one")) {
                            menu.setTopDisplay("no");
                            serviceMen.update(menu);
                        }
                    }
                    mv.setTopDisplay("two");

                }

                serviceMen.update(mv);

                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());
                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MENU, ForwardConst.CMD_INDEX);

            }

        }

    }

}
