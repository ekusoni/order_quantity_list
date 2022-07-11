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
     * 指定された材料名を条件にデータを取得し、MaterialMasterViewのリストで返却する
     * @param name 材料名
     * @return
     */
    public List<MaterialMasterView> searchName(String name){
        List<MaterialMaster> searchNames=em.createNamedQuery(JpaConst.Q_MATM_SEARCH_BY_NAME,MaterialMaster.class)
                .setParameter(JpaConst.JPQL_PARM_NAME,name+"%")
                .getResultList();


        return MaterialMasterConverter.toViewList(searchNames);
    }

    /**
     * 指定された単位を条件にデータを取得し、MaterialMasterViewのリストで返却する
     * @param unit 単位
     * @return 表示するデータのリスト
     */
    public List<MaterialMasterView> searchUnit(String unit){
        List<MaterialMaster> searchUnits=em.createNamedQuery(JpaConst.Q_MATM_SEARCH_BY_UNIT,MaterialMaster.class)
                .setParameter(JpaConst.JPQL_PARM_UNIT,unit+"%")
                .getResultList();

        return MaterialMasterConverter.toViewList(searchUnits);
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
        List<String> errors=MaterialMasterValidator.validate(this, mmv,true);

        //バリデーションエラーがなければデータを登録する
        if(errors.size()== 0) {
            createInternal(mmv);
        }

        //エラーを返却(エラーがなければ0件の空リスト)
        return errors;

    }

    public List<String> update(MaterialMasterView mmv){

        //idを条件に登録済みの材料情報を取得する
        MaterialMasterView savedMatM=findOne(mmv.getId());

        boolean validateName= false;
        if(!savedMatM.getName().equals(mmv.getName())) {
            //材料名を変更する場合

            //料理名についてのバリデーションを行う
            validateName=true;
            savedMatM.setName(mmv.getName());
        }

        savedMatM.setUnit(mmv.getUnit());//変更後の単位を設定する

        //更新内容についてバリデーションを行う
        List<String> errors=MaterialMasterValidator.validate(this, mmv,validateName);

        //バリデーションエラーがなければデータを更新する
        if(errors.size() ==0) {
            updateInternal(savedMatM);
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

    private void updateInternal(MaterialMasterView mmv) {

        em.getTransaction().begin();
        MaterialMaster mm=findOneInternal(mmv.getId());
        MaterialMasterConverter.copyViewToModel(mm, mmv);
        em.getTransaction().commit();


    }

}
