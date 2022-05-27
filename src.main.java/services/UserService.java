package services;

import java.util.List;

import javax.persistence.NoResultException;

import actions.views.UserConverter;
import actions.views.UserView;
import constants.JpaConst;
import models.User;
import models.validators.UserValidator;
import utils.EncryptUtil;

/**
 * 利用者テーブルの操作に関わる処理を行うクラス
 *
 */
public class UserService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得しUserViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<UserView> getPerpage(int page){
        List<User> users=em.createNamedQuery(JpaConst.Q_USE_GET_ALL, User.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return UserConverter.toViewList(users);


    }

    /**
     * 利用者テーブルのデータの件数を取得し、返却する
     * @return 従業員テーブルのデータの件数
     */
    public long countAll() {
        long useCount=(long) em.createNamedQuery(JpaConst.Q_USE_COUNT,Long.class)
                .getSingleResult();


        return useCount;
    }

    /**
     * ユーザー番号、パスワードを条件に取得したデータをUserViewのインスタンスで返却する
     * @param code ユーザー番号
     * @param plainpass パスワード文字列
     * @param pepper pepper文字列
     * @return 取得したデータのインスタンス 取得できない場合null
     */
    public UserView findOne(String code,String plainPass,String pepper) {
        User u=null;
        try {
            //パスワードのハッシュ化
            String pass=EncryptUtil.getPasswordEncrypt(plainPass,pepper);

            //ユーザー番号とハッシュ化済パスワードを条件に未削除の利用者を1件取得する
            u =em.createNamedQuery(JpaConst.Q_USE_GET_BY_CODE_AND_PASS,User.class)
                   .setParameter(JpaConst.JPQL_PARM_CODE,code)
                   .setParameter(JpaConst.JPQL_PARM_PASSWORD,pass)
                   .getSingleResult();
        }catch(NoResultException ex) {
        }


        return UserConverter.toView(u);

    }

    /**
     * idを条件に取得したデータをUserViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public UserView findOne(int id) {
        User u=findOneInternal(id);
        return UserConverter.toView(u);
    }


    /**
     * ユーザー番号を条件に該当するデータの件数を取得し、返却する
     * @param code ユーザー番号
     * @return 該当するデータの件数
     */
    public long countByCode(String code) {

        //指定したユーザー番号を保持する利用者の件数を取得する
        long users_count=(long) em.createNamedQuery(JpaConst.Q_USE_COUNT_RESISTERED_BY_CODE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_CODE, code)
                .getSingleResult();
        return users_count;
    }


    /**
     * 画面から入力された利用者の登録内容をもとにデータを1件作成し、利用者テーブルに登録する
     * @param uv 画面から入力された利用者の登録内容
     * @param pepper pepper文字列
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(UserView uv,String pepper){


        //パスワードをハッシュ化して設定
        String pass=EncryptUtil.getPasswordEncrypt(uv.getPassword(),pepper);
        uv.setPassword(pass);

        //登録内容のバリデーションを行う
        List<String> errors=UserValidator.validate(this,uv,true,true);

        //バリデーションエラーがなければデータを登録する
        if(errors.size()==0) {
            create(uv);
        }

        //エラーを返却(エラーがなければ0件の空リスト)
        return errors;
    }


    /**
     * 画面から入力された利用者の更新内容をもとにデータを1件作成し、利用者テーブルを更新する
     * @param uv 画面から入力された利用者の登録内容
     * @param pepper pepper文字列
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(UserView uv,String pepper){

        //idを条件に登録済みの利用者情報を取得する
        UserView savedUse=findOne(uv.getId());

        boolean validateCode=false;
        if(!savedUse.getCode().equals(uv.getCode())) {
            //ユーザー番号を更新する場合

            //ユーザー番号についてのバリデーションを行う
            validateCode=true;
            //変更後のユーザー番号を設定する
            savedUse.setCode(uv.getCode());
        }


        boolean validatePass=false;
        if(uv.getPassword() !=null && !uv.getPassword().equals("")) {
            //パスワードに入力がある場合

            //パスワードについてのバリデーションを行う
            validatePass=true;

            //変更後のパスワードをハッシュ化し設定する
            savedUse.setPassword(EncryptUtil.getPasswordEncrypt(uv.getPassword(),pepper));

        }

        savedUse.setName(uv.getName());//変更後の氏名を設定する
        savedUse.setAuthorFlag(uv.getAuthorFlag());//変更後の作成者フラグを設定する


        //更新内容についてバリテーションを行う
        List<String> errors=UserValidator.validate(this, uv, validateCode, validatePass);

        //バリデーションがなければデータを更新する
        if(errors.size()==0) {
            update(savedUse);
        }

        //エラーを返却(エラーがなければ0件の空リスト)
        return errors;

    }

    /**
     * idを条件に利用者データを論理削除する
     * @param id
     */
    public void destroy(Integer id) {


        //idを条件に登録済みの利用者情報を取得する
        UserView savedUse=findOne(id);

        //論理削除フラグをたてる
        savedUse.setDeleteFlag(JpaConst.USE_DEL_TRUE);

        //更新処理を行う
        update(savedUse);
    }

    /**
     * ユーザー番号とパスワードを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param code ユーザー番号
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却する(成功:true 失敗:false)
     */
    public Boolean validateLogin(String code,String plainPass,String pepper) {

        boolean isValidUser=false;
        if(code !=null && !code.equals("") && plainPass != null && !plainPass.equals("")) {
            UserView uv= findOne(code,plainPass,pepper);

            if(uv != null&& uv.getId() !=null) {

                //データが取得できた場合、認証成功
                isValidUser=true;
            }
        }

        //認証結果を返却する
        return isValidUser;
    }

    /**
     * idを条件にデータを1件取得し、Userのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private User findOneInternal(int id) {
        User u =em.find(User.class,id);

        return u;
    }

    /**
     * 利用者データを1件登録する
     * @param uv 利用者データ
     * @return 登録結果(成功:true 失敗:false)
     */
    private void create(UserView uv) {

        em.getTransaction().begin();
        em.persist(UserConverter.toModel(uv));
        em.getTransaction().commit();
    }


    /**
     * 利用者データを更新する
     * @param uv 画面から入力された利用者の登録内容
     */
    private void update(UserView uv) {
        em.getTransaction().begin();
        User u= findOneInternal(uv.getId());
        UserConverter.copyViewToModel(u, uv);
        em.getTransaction().commit();

    }


}
