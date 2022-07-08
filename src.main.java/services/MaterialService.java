package services;

import java.util.List;

import actions.views.MaterialConverter;
import actions.views.MaterialView;
import constants.JpaConst;
import models.Material;

/**
 * 材料テーブルの操作に関わる処理を行うクラス
 */
public class MaterialService extends ServiceBase {

    /**
     * 詳細画面等の表示の時に必要な材料(量)の全データを取得し、MaterialViewのリストで返却する
     * @return 表示するデータのリスト
     */
    public List<MaterialView> getPage(){
        List<Material> materials=em.createNamedQuery(JpaConst.Q_MAT_GET_ALL,Material.class)
                .getResultList();

        return MaterialConverter.toViewList(materials);
    }


    /**
     * idを条件に取得したデータをMaterialViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public MaterialView findOne(int id) {
        Material m=findOneInternal(id);
        return MaterialConverter.toView(m);
    }






    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得したデータのインスタンス
     */
    private Material findOneInternal(int id) {
        return em.find(Material.class, id);
    }

    /**
     * 材料データを1件登録する
     * @param mv 材料データ
     */
    public void create(MaterialView mv) {

        em.getTransaction().begin();
        em.persist(MaterialConverter.toModel(mv));
        em.getTransaction().commit();

    }

    /**
     * 材料データを1件登録する
     * @param mv 材料データ
     */
    public void update(MaterialView mv) {

        em.getTransaction().begin();
        Material m=findOneInternal(mv.getId());
        MaterialConverter.copyViewToModel(m, mv);
        em.getTransaction().commit();

    }

    /**
     * 材料データを1件削除する
     * @param mv 材料データ
     */

    public void destroy(MaterialView mv) {
        em.getTransaction().begin();
        Material m=findOneInternal(mv.getId());
        em.remove(m);
        em.getTransaction().commit();
    }




}
