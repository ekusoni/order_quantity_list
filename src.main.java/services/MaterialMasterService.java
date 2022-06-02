package services;

import java.util.List;

import actions.views.MaterialMasterConverter;
import actions.views.MaterialMasterView;
import constants.JpaConst;
import models.MaterialMaster;
import models.validators.MaterialMasterValidator;

public class MaterialMasterService extends ServiceBase{


    public List<MaterialMasterView> getPerPage(int page){
        List<MaterialMaster> materialMasters=em.createNamedQuery(JpaConst.Q_MATM_GET_ALL, MaterialMaster.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page-1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return MaterialMasterConverter.toViewList(materialMasters);

    }


    public List<MaterialMasterView> getSelectList(){
        List<MaterialMaster> materialMasters = em.createNamedQuery(JpaConst.Q_MATM_GET_ALL, MaterialMaster.class)
                .getResultList();


        return MaterialMasterConverter.toViewList(materialMasters);
    }

    public long countAll() {
        long matMCount=(long)em.createNamedQuery(JpaConst.Q_MATM_COUNT,Long.class)
                .getSingleResult();


        return matMCount;
    }


    public long countByName(String name) {

        //指定した材料名を保持する材料の件数を取得する
        long materialMasters_count=(long)em.createNamedQuery(JpaConst.Q_MATM_COUNT_RESISTERED_BY_NAME,Long.class)
                .setParameter(JpaConst.JPQL_PARM_NAME,name)
                .getSingleResult();
        return materialMasters_count;
    }

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

    private void createInternal(MaterialMasterView mmv) {

        em.getTransaction().begin();
        em.persist(MaterialMasterConverter.toModel(mmv));
        em.getTransaction().commit();
    }

}
