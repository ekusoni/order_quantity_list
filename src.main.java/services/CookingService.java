package services;

import java.util.List;

import javax.persistence.NoResultException;

import actions.views.CookingConverter;
import actions.views.CookingView;
import constants.JpaConst;
import models.Cooking;

/**
 *
 * 料理テーブルの操作に関わる処理を行うクラス
 *
 */
public class CookingService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、CookingViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<CookingView> getAllPerPage(int page){
        List<Cooking> cookings=em.createNamedQuery(JpaConst.Q_COO_GET_ALL,Cooking.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return CookingConverter.toViewList(cookings);
    }

    /**
     * プルダウンメニューで使用するデータを取得しCookingViewのリストで返却する
     * @return 表示するデータのリスト
     */
    public List<CookingView> getSelectList(){
        List<Cooking> cookings=em.createNamedQuery(JpaConst.Q_COO_GET_ALL,Cooking.class)
                .getResultList();



        return CookingConverter.toViewList(cookings);
    }

    /**
     * 指定された料理名を条件にデータを取得し、CookingViewのリストで返却する
     * @param name 料理名
     * @return 表示するデータのリスト
     */
    public List<CookingView> searchName(String name){
        List<Cooking> cookings=em.createNamedQuery(JpaConst.Q_COO_SEARCH_BY_NAME, Cooking.class)
                .setParameter(JpaConst.JPQL_PARM_NAME, name+"%")
                .getResultList();

        return CookingConverter.toViewList(cookings);

    }



    /**
     * 料理テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long cookings_count=(long)em.createNamedQuery(JpaConst.Q_COO_COUNT,Long.class)
                .getSingleResult();
        return cookings_count;

    }

    /**
     * idを条件に取得したデータをCookingViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public CookingView findOne(int id) {
        Cooking c =findOneInternal(id);
        return CookingConverter.toView(c);
    }




    /**
     * 料理名を条件に該当するデータの件数取得し、返却する
     * @param name 料理名
     * @return 該当するデータの件数
     */
    public long countByName(String name) {

        //指定した料理名を保持する料理の件数を取得する
        long cookings_count=(long)em.createNamedQuery(JpaConst.Q_COO_COUNT_RESISTERED_BY_NAME,Long.class)
                .setParameter(JpaConst.JPQL_PARM_NAME, name)
                .getSingleResult();
        return cookings_count;
    }


    /**
     * 料理名を条件に取得したデータをCookingViewのインスタンスで返却する
     * @param name
     * @return
     */
    public CookingView findName(String name) {
        Cooking c = null;

        try {
            c=em.createNamedQuery(JpaConst.Q_COO_GET_BY_NAME,Cooking.class)
                    .setParameter(JpaConst.JPQL_PARM_NAME, name)
                    .getSingleResult();

        }catch(NoResultException ex) {
        }

        return CookingConverter.toView(c);


    }





    /**
     * idを条件にデータを1件取得し、Cookingのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Cooking findOneInternal(int id) {
        Cooking c=em.find(Cooking.class, id);

        return c;
    }



    /**
     * 料理データを1件登録する
     * @param cv 料理データ
     */
    public void create(CookingView cv) {

        em.getTransaction().begin();
        em.persist(CookingConverter.toModel(cv));
        em.getTransaction().commit();

    }

    /**
     * 料理データを更新する
     * @param cv 画面から入力された料理の登録内容
     */
    public void update(CookingView cv) {

        em.getTransaction().begin();
        Cooking c=findOneInternal(cv.getId());
        CookingConverter.copyViewToModel(c, cv);
        em.getTransaction().commit();
    }

}
