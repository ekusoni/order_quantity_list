package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.MaterialMasterView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.MaterialMasterService;



public class MaterialMasterAction extends ActionBase {

    /**
     * メソッドを実行する
     */
    private MaterialMasterService service;
    @Override
    public void process() throws ServletException, IOException {
        service=new MaterialMasterService();

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

        //閲覧者かどうかのチェック
        if (checkAuthor()) {

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

            //一覧画面を表示
            forward(ForwardConst.FW_MATM_INDEX);
        }
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        //閲覧者かどうかのチェック
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
        if (checkAuthor()) {

            //パラメータの値を元に材料情報のインスタンスを作成する
            MaterialMasterView mmv = new MaterialMasterView(
                    null,
                    getRequestParam(AttributeConst.MATM_NAME),
                    getRequestParam(AttributeConst.MATM_UNIT));





            //材料情報登録
            List<String> errors=service.create(mmv);

            if(errors.size() > 0) {
                //登録中にエラーがあった場合
                putRequestScope(AttributeConst.TOKEN,getTokenId());//CSRF対策用トークン
                putRequestScope(AttributeConst.MATERIALM,mmv);//入力された材料情報
                putRequestScope(AttributeConst.ERR,errors);//エラーのリスト


                //新規登録画面を再表示
                forward(ForwardConst.FW_MATM_NEW);

            }else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putRequestScope(AttributeConst.FLUSH,MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MATM,ForwardConst.CMD_INDEX);
            }


        }
    }




    /**
     * ログイン中の利用者が作成者かどうかチェックし、作成者でなければエラー画面を表示
     * true: 作成者 false: 作成者ではない
     * @throws ServletException
     * @throws IOException
     */

    private boolean checkAuthor() throws ServletException, IOException {
        //セッションからログイン中の利用者情報を取得
        UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USE);

        //閲覧者でなければエラー画面を表示
        if (uv.getAuthorFlag() != AttributeConst.ROLE_AUTHOR.getIntegerValue()) {

            forward(ForwardConst.FW_ERR_UNKNOWN);
            return false;
        } else {
            return true;
        }
    }

}


