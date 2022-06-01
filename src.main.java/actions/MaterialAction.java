package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.MaterialView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.MaterialService;

public class MaterialAction extends ActionBase {

    private MaterialService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {
        service = new MaterialService();

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
            List<MaterialView> materials = service.getPerPage(page);

            //全ての材料データの件数を取得
            long materialsCount = service.countAll();

            putRequestScope(AttributeConst.MATERIALS, materials);//取得した材料データ
            putRequestScope(AttributeConst.MAT_COUNT, materialsCount);//全ての材料データの件数
            putRequestScope(AttributeConst.PAGE, page);//ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);//1ページに表示するレコードの数

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替えセッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_MAT_INDEX);
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
