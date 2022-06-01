package services;

import java.util.List;

import actions.views.MaterialConverter;
import actions.views.MaterialView;
import constants.JpaConst;
import models.Material;
import models.validators.MaterialValidator;

/**
 *材料テーブルの操作に関わる処理を行うクラス
 */
public class MaterialService extends ServiceBase {


    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、MaterialViewのリストで返却する
     * @param page数
     * @return 表示するデータのリスト
     */
    public List<MaterialView> getPerPage(int page){
        List<Material> materials=em.createNamedQuery(JpaConst.Q_MAT_GET_ALL,Material.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return MaterialConverter.toViewList(materials);
    }

    /**
     * 選択要素に使用するデータを取得し、MaterialViewのリストで返却する
     * @return 使用するデータのリスト
     */
    public List<MaterialView> getSelectList(){
        List<Material> materials = em.createNamedQuery(JpaConst.Q_MAT_GET_ALL, Material.class)
                .getResultList();


        return MaterialConverter.toViewList(materials);
    }

    /**
     * 材料テーブルのデータの件数を取得し、返却する
     * @return 材料テーブルのデータの件数
     */
    public long countAll() {
        long matCount=(long)em.createNamedQuery(JpaConst.Q_MAT_COUNT,Long.class)
                .getSingleResult();


        return matCount;
    }


    /**
     * 材料名を条件に該当するデータの件数を取得し、返却する
     * @param name 材料名
     * @return 該当するデータの件数
     */
    public long countByName(String name) {

        //指定した材料名を保持する材料の件数を取得する
        long materials_count=(long)em.createNamedQuery(JpaConst.Q_MAT_COUNT_RESISTERED_BY_CODE,Long.class)
                .setParameter(JpaConst.JPQL_PARM_NAME,name)
                .getSingleResult();
        return materials_count;
    }


    /**
     * 画面から入力された材料の登録内容を元にデータを1件作成し、材料テーブルに登録する
     * @param mv 画面から入力された材料の登録内容
     * @return バリテーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(MaterialView mv){

        //登録内容のバリテーションを行う
        List<String> errors=MaterialValidator.validate(this,mv);

        //バリデーションエラーがなければデータを登録する
        if(errors.size()== 0) {
            em.getTransaction().begin();
            em.persist(MaterialConverter.toModel(mv));
            em.getTransaction().commit();
        }

        //エラーを返却(エラーがなければ0件の空リスト)
        return errors;

    }


}
