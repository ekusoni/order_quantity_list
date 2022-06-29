package services;

import java.util.List;

import actions.views.MaterialMasterConverter;
import actions.views.MaterialMasterView;
import constants.JpaConst;
import models.MaterialMaster;
import models.validators.MaterialMasterValidator;

public class MaterialMasterService extends ServiceBase{

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得しMaterialMasterViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<MaterialMasterView> getPerPage(int page){
        List<MaterialMaster> materialMasters=em.createNamedQuery(JpaConst.Q_MATM_GET_ALL, MaterialMaster.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return MaterialMasterConverter.toViewList(materialMasters);

    }


    /**
     * 料理の登録ページで表示する材料データを取得し、MaterialMasterViewのリストで返却する
     *@return表示するデータのリスト
     */
    public List<MaterialMasterView> getSelectList(){
        List<MaterialMaster> materialMasters = em.createNamedQuery(JpaConst.Q_MATM_GET_ALL, MaterialMaster.class)
                .getResultList();


        return MaterialMasterConverter.toViewList(materialMasters);
    }


    /**
     * 材料テーブルのデータの件数を取得し、返却する
     * @return 従業員テーブルのデータの件数
     */
    public long countAll() {
        long matMCount=(long)em.createNamedQuery(JpaConst.Q_MATM_COUNT,Long.class)
                .getSingleResult();


        return matMCount;
    }



    /**
     * 材料名を条件に該当するデータの件数を取得し、返却する
     * @param name 材料名
     * @return 該当するデータの件数
     */
    public long countByName(String name) {

        //指定した材料名を保持する材料の件数を取得する
        long materialMasters_count=(long)em.createNamedQuery(JpaConst.Q_MATM_COUNT_RESISTERED_BY_NAME,Long.class)
                .setParameter(JpaConst.JPQL_PARM_NAME,name)
                .getSingleResult();
        return materialMasters_count;
    }


    /**
     * idを条件に取得したデータをMaterialMasterViewのインスタンスで返却する
     * @param id
     * @return 取得したデータのインスタンス
     */
    public MaterialMasterView findOne(int id) {
        return MaterialMasterConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された材料の登録内容を元にデータを1件作成し、材料テーブルに登録する
     * @param mmv 画面から入力された材料の登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(MaterialMasterView mmv){



        //登録内容のバリテーションを行う
        List<String> errors=MaterialMasterValidator.validate(this, mmv);

        //バリデーションエラーがなければデータを登録する
        if(errors.size()== 0) {
            createInternal(mmv);
        }

        //エラーを返却(エラーがなければ0件の空リスト)
        return errors;

    }


    /**
     * idを条件にデータを1件取得し、MaterialMasterのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private MaterialMaster findOneInternal(int id) {
        return em.find(MaterialMaster.class, id);
    }

    /**
     * 材料データを1件登録する
     * @param mmv 材料データ
     */
    private void createInternal(MaterialMasterView mmv) {

        em.getTransaction().begin();
        em.persist(MaterialMasterConverter.toModel(mmv));
        em.getTransaction().commit();
    }

}
