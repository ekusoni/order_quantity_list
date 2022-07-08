package services;

import java.util.List;

import actions.views.CookingSlaveConverter;
import actions.views.CookingSlaveView;
import constants.JpaConst;
import models.CookingSlave;

/**
 * 料理テーブルの操作に関わる処理を行うクラス
 */

public class CookingSlaveService extends ServiceBase{

    /**
     * 詳細画面等の表示の時に必要な料理(量)の全データを取得し、CookingSlaveViewのリストで返却する
     * @return 表示するデータのリスト
     */
    public List<CookingSlaveView> getPage(){
        List<CookingSlave> cookingSlaves=em.createNamedQuery(JpaConst.Q_COS_GET_ALL,CookingSlave.class)
                .getResultList();

        return CookingSlaveConverter.toViewList(cookingSlaves);
    }

    /**
     * 編集画面等に使用する料理(量)のデータをメニューidを条件に取得し、CookingSlaveViewのリストで返却する
     * @param id メニューid
     * @return 表示するデータのリスト
     */
    public List<CookingSlaveView> getMenus(int id) {
        List<CookingSlave> cookingSlaves=em.createNamedQuery(JpaConst.Q_COS_GET_BY_MENU_ID,CookingSlave.class)
                .setParameter(JpaConst.JPQL_PARM_ID, id)
                .getResultList();

        return CookingSlaveConverter.toViewList(cookingSlaves);
    }

    /**
     * idを条件に取得したデータをCookingSlaveViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public CookingSlaveView findOne(int id) {
        CookingSlave cs=findOneInternal(id);
        return CookingSlaveConverter.toView(cs);
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得してデータのインスタンス
     */
    private CookingSlave findOneInternal(int id) {
        return em.find(CookingSlave.class, id);
    }

    /**
     * 料理データ(量)を1件登録する
     * @param csv 料理データ
     */
    public void create(CookingSlaveView csv) {
        em.getTransaction().begin();
        em.persist(CookingSlaveConverter.toModel(csv));
        em.getTransaction().commit();
    }

    /**
     * 料理データ(量)を1件更新する
     * @param csv 料理データ
     */
    public void update(CookingSlaveView csv) {
        em.getTransaction().begin();
        CookingSlave cs =findOneInternal(csv.getId());
        CookingSlaveConverter.copyViewToModel(cs, csv);
        em.getTransaction().commit();
    }

    /**
     * 料理(量)データを1件削除する
     * @param csv 材料(量)データ
     */
    public void destroy(CookingSlaveView csv) {
        em.getTransaction().begin();
        CookingSlave cs=findOneInternal(csv.getId());
        em.remove(cs);
        em.getTransaction().commit();

    }





}
