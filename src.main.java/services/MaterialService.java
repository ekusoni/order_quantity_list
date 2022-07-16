package services;

import java.util.List;

import actions.views.MaterialConverter;
import actions.views.MaterialView;
import constants.JpaConst;
import models.Material;
import models.MaterialDistinct;
import models.MaterialGroupBy;

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
     * 指定された材料名を条件にデータを取得し、MaterialViewのリストで返却する
     * @param name 材料名
     * @return 表示するデータのリスト
     */
    public List<MaterialView> searcMatMName(String name){
        List<Material> materials=em.createNamedQuery(JpaConst.Q_MAT_SEARCH_BY_MATM_NAME,Material.class)
                .setParameter(JpaConst.JPQL_PARM_NAME, name+"%")
                .getResultList();

        return MaterialConverter.toViewList(materials);
    }

    /**
     * 指定された材料idと料理idを条件にデータを取得しMaterialGroupByのリストで返却する
     * @param id 材料id
     * @param cooId 料理id
     * @return 計算に使用するデータのリスト
     */
    public List<MaterialGroupBy> sumMatAmount(int id,int cooId) {
        List<MaterialGroupBy> materialAmounts=em.createQuery(JpaConst.Q_MAT_SUM_AMOUNT_BY_MATM_ID_AND_COO_ID_DEF,MaterialGroupBy.class)
                .setParameter(JpaConst.JPQL_PARM_ID, id)
                .setParameter(JpaConst.JPQL_PARM_ID_COO,cooId)
                .getResultList();

        return materialAmounts;
    }

    /**
     * 材料idと料理idが重複しているデータを除いて取得しMaterialDistinctのリストで返却する
     * @return 計算に使用するデータのリスト
     */
    public List<MaterialDistinct> distinctMaterial(){
        List<MaterialDistinct> distinctMaterials=em.createQuery(JpaConst.Q_MAT_DISTINCT_MATM_ID_AND_COO_ID_DEF,MaterialDistinct.class)
                .getResultList();

        return distinctMaterials;

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
