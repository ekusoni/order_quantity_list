package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.UserService;

public class UserAction extends ActionBase {

    private UserService service;

    @Override
    public void process() throws ServletException, IOException {
        service = new UserService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<UserView> users = service.getPerpage(page);

        //全ての利用者データの件数を取得
        long userCount = service.countAll();

        putRequestScope(AttributeConst.USERS, users);//取得した利用者データ
        putRequestScope(AttributeConst.USE_COUNT, userCount);//全ての利用者データの件数
        putRequestScope(AttributeConst.PAGE, page);//ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);//1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);

        }

        //一覧画面を表示
        forward(ForwardConst.FW_USE_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId());//CSRF対策用トークン
        putRequestScope(AttributeConst.USER, new UserView());//空の利用者インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_USE_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //パラメータの値を元に利用者情報のインスタンスを作成する
            UserView uv = new UserView(
                    null,
                    getRequestParam(AttributeConst.USE_CODE),
                    getRequestParam(AttributeConst.USE_NAME),
                    getRequestParam(AttributeConst.USE_PASS),
                    toNumber(getRequestParam(AttributeConst.USE_AUTHOR_FLAG)),
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //利用者情報登録
            List<String> errors = service.create(uv, pepper);

            if(errors.size()>0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN,getTokenId());//CSRF対策用トークン
                putRequestScope(AttributeConst.USER,uv);//入力された利用者情報
                putRequestScope(AttributeConst.ERR,errors);//エラーのリスト



                //新規登録画面を再表示
                forward(ForwardConst.FW_USE_NEW);
            }else {
                //更新中にエラーが無かった場合


                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH,MessageConst.I_REGISTERED.getMessage());



                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_USE,ForwardConst.CMD_INDEX);
            }

        }

    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException,IOException{

        //idを条件に利用者データを取得する
        UserView uv=service.findOne(toNumber(getRequestParam(AttributeConst.USE_ID)));

        if(uv==null || uv.getDeleteFlag()==AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //データが取得できなかった、または倫理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;

        }


        putRequestScope(AttributeConst.USER,uv);//取得した利用者情報

        //詳細画面を表示
        forward(ForwardConst.FW_USE_SHOW);
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException,IOException{


        //idを条件に利用者データを取得する
        UserView uv=service.findOne(toNumber(getRequestParam(AttributeConst.USE_ID)));


        if(uv==null || uv.getDeleteFlag()==AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {


            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }


        putRequestScope(AttributeConst.TOKEN,getTokenId());//CSRF対策用トークン
        putRequestScope(AttributeConst.USER,uv);//取得した利用者情報


        //編集画面を表示する
        forward(ForwardConst.FW_USE_EDIT);
    }


    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException,IOException{

        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //パラメータの値を元に利用者情報のインスタンスを作成する
            UserView uv=new UserView(
                    toNumber(getRequestParam(AttributeConst.USE_ID)),
                    getRequestParam(AttributeConst.USE_CODE),
                    getRequestParam(AttributeConst.USE_NAME),
                    getRequestParam(AttributeConst.USE_PASS),
                    toNumber(getRequestParam(AttributeConst.USE_AUTHOR_FLAG)),
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper=getContextScope(PropertyConst.PEPPER);

            //利用者情報更新
            List<String> errors=service.update(uv, pepper);

            if(errors.size() >0) {
                //更新中にエラーが発生した場合


                putRequestScope(AttributeConst.TOKEN,getTokenId());//CSRF対策用トークン
                putRequestScope(AttributeConst.USER,uv);//入力された利用者情報
                putRequestScope(AttributeConst.ERR,errors);//エラーのリスト


                //編集画面を再表示
                forward(ForwardConst.FW_USE_EDIT);

            }else {
                //更新中にエラーが無かった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH,MessageConst.I_UPDATED.getMessage());


                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_USE,ForwardConst.CMD_INDEX);
            }
        }
    }


    public void destroy() throws ServletException,IOException{

        //CSRF対策tokenのチェック
        if(checkToken()) {

            //idを条件に利用者データを論理削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.USE_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH,MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_USE,ForwardConst.CMD_INDEX);
        }

    }

}
