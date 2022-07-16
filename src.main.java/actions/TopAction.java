package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

import actions.views.CookingSlaveView;
import actions.views.MaterialView;
import actions.views.MenuView;
import constants.AttributeConst;
import constants.ForwardConst;
import models.MaterialDistinct;
import models.MaterialGroupBy;
import services.CookingService;
import services.CookingSlaveService;
import services.MaterialMasterService;
import services.MaterialService;
import services.MenuService;

/**
 * トップページに関する処理を行うActionクラス
 *
 *
 */
public class TopAction extends ActionBase {

    private MenuService serviceMen;
    private CookingSlaveService serviceCos;
    private MaterialService serviceMat;
    private MaterialMasterService serviceMas;
    private CookingService serviceCoo;

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        serviceMen = new MenuService();
        serviceCos = new CookingSlaveService();
        serviceMat = new MaterialService();
        serviceMas = new MaterialMasterService();
        serviceCoo = new CookingService();

        //メソッドを実行する
        invoke();

        serviceCoo.close();
        serviceMas.close();
        serviceMat.close();
        serviceCos.close();
        serviceMen.close();
    }

    /**
     * 一覧画面を表示する
     */
    public void index() throws ServletException, IOException {

        List<MenuView> menus = serviceMen.getPage();//メニューの全データ
        List<CookingSlaveView> cookingSlaves = serviceCos.getPage();//料理の全データ(量を含めた)
        List<CookingSlaveView> csvs = new ArrayList<>();//料理のtopDisplayの項目がoneの物(トップページの上部に出力される)、twoの物(トップページ下部に出力される)を入れる
        List<MaterialView> mvs = new ArrayList<>();//料理で使われている一つ一つの材料の情報
        List<Integer> materialMasterIds = new ArrayList<>();//メニューに使われている材料のid
        List<Integer> cookingIds = new ArrayList<>();//メニューに使われている料理のid
        List<MaterialView> mvOnes = new ArrayList<>();//トップページ上部に出力される材料の情報を入れる
        List<MaterialView> mvTwos = new ArrayList<>();//トップページ下部に出力される材料の情報を入れる


        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションから削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //メニューがoneの料理データを取得する
        for (CookingSlaveView cookingSlave : cookingSlaves) {
            if (cookingSlave.getMenu().getTopDisplay().equals("one")) {
                csvs.add(cookingSlave);
            }
        }

        //メニューに登録されている料理の被りを失くす
        for (CookingSlaveView csv : csvs) {
            cookingIds.add(csv.getCooking().getId());
        }
        List<Integer> cvsNoDuplications = cookingIds.stream().distinct().collect(Collectors.toList());


        //材料の重複部分を失くす
        List<MaterialDistinct> materialDistincts = serviceMat.distinctMaterial();

        //同一の料理で尚且つその料理に使われている同じ材料を取得する(材料の数量を合算したうえで)。
        for (Integer cvsNoDuplication : cvsNoDuplications) {
            for (MaterialDistinct materialDistinct : materialDistincts) {
                if (cvsNoDuplication == materialDistinct.getCookingId()) {
                    //同一の料理で尚且つ同一の材料の時に入り、材料の数量を取得する
                    List<MaterialGroupBy> amounts = serviceMat.sumMatAmount(materialDistinct.getMaterialMasterId(),
                            cvsNoDuplication);

                    for (MaterialGroupBy amount : amounts) {
                        if (amount.getAmount() > 0) {
                            MaterialView mv = new MaterialView(
                                    null,
                                    serviceMas.findOne(materialDistinct.getMaterialMasterId()),
                                    serviceCoo.findOne(cvsNoDuplication),
                                    Integer.parseInt(String.valueOf((amount.getAmount()))));
                            mvs.add(mv);
                        }
                    }
                }
            }
        }

        //同一の料理の数量を計算し、その料理で使われている材料をidごとに計算し取得する
        for (Integer cvsNoDuplication : cvsNoDuplications) {
            int amount = 0;
            for (CookingSlaveView csv : csvs) {
                if (cvsNoDuplication == csv.getCooking().getId()) {
                    amount = csv.getAmount() + amount;
                }
            }
            for (MaterialView mv : mvs) {
                if (cvsNoDuplication == mv.getCooking().getId()) {
                    mv.setAmount(mv.getAmount() * amount);
                }
            }
        }

        //メニューに使われている材料の被りを失くす
        for (MaterialView mv : mvs) {
            materialMasterIds.add(mv.getMaterialMaster().getId());
        }
        List<Integer> mmvsNoDuplications = materialMasterIds.stream().distinct().collect(Collectors.toList());

        //同一の材料を足してjspに受け渡す用のデータとして取得する
        for (Integer mmvsNoDuplication : mmvsNoDuplications) {
            int amount=0;
            for (MaterialView mv : mvs) {
                if (mmvsNoDuplication == mv.getMaterialMaster().getId()) {
                    amount=mv.getAmount()+amount;
                }
            }
            MaterialView mvOne=new MaterialView(
                    null,
                    serviceMas.findOne(mmvsNoDuplication),
                    null,
                    amount);
            mvOnes.add(mvOne);

        }

        //メニューがtwoの料理データを取得する
        csvs.clear();
        for (CookingSlaveView cookingSlave : cookingSlaves) {
            if (cookingSlave.getMenu().getTopDisplay().equals("two")) {
                csvs.add(cookingSlave);
            }
        }

        //メニューに登録されている料理の被りを失くす
        cookingIds.clear();
        for (CookingSlaveView csv : csvs) {
            cookingIds.add(csv.getCooking().getId());
        }
        cvsNoDuplications = cookingIds.stream().distinct().collect(Collectors.toList());


        //材料の重複部分を失くす
        materialDistincts = serviceMat.distinctMaterial();

        //同一の料理で尚且つその料理に使われている同じ材料を取得する(材料の数量を合算したうえで)。
        mvs.clear();
        for (Integer cvsNoDuplication : cvsNoDuplications) {
            for (MaterialDistinct materialDistinct : materialDistincts) {
                if (cvsNoDuplication == materialDistinct.getCookingId()) {
                    //同一の料理で尚且つ同一の材料の時に入り、材料の数量を取得する
                    List<MaterialGroupBy> amounts = serviceMat.sumMatAmount(materialDistinct.getMaterialMasterId(),
                            cvsNoDuplication);

                    for (MaterialGroupBy amount : amounts) {
                        if (amount.getAmount() > 0) {
                            MaterialView mv = new MaterialView(
                                    null,
                                    serviceMas.findOne(materialDistinct.getMaterialMasterId()),
                                    serviceCoo.findOne(cvsNoDuplication),
                                    Integer.parseInt(String.valueOf((amount.getAmount()))));
                            mvs.add(mv);
                        }
                    }
                }
            }
        }

        //同一の料理の数量を計算し、その料理で使われている材料をidごとに計算し取得する
        for (Integer cvsNoDuplication : cvsNoDuplications) {
            int amount = 0;
            for (CookingSlaveView csv : csvs) {
                if (cvsNoDuplication == csv.getCooking().getId()) {
                    amount = csv.getAmount() + amount;
                }
            }
            for (MaterialView mv : mvs) {
                if (cvsNoDuplication == mv.getCooking().getId()) {
                    mv.setAmount(mv.getAmount() * amount);
                }
            }
        }

        //メニューに使われている材料の被りを失くす
        materialMasterIds.clear();
        for (MaterialView mv : mvs) {
            materialMasterIds.add(mv.getMaterialMaster().getId());
        }
        mmvsNoDuplications = materialMasterIds.stream().distinct().collect(Collectors.toList());

        //同一の材料を足してjspに受け渡す用のデータとして取得する
        for (Integer mmvsNoDuplication : mmvsNoDuplications) {
            int amount=0;
            for (MaterialView mv : mvs) {
                if (mmvsNoDuplication == mv.getMaterialMaster().getId()) {
                    amount=mv.getAmount()+amount;
                }
            }
            MaterialView mvTwo=new MaterialView(
                    null,
                    serviceMas.findOne(mmvsNoDuplication),
                    null,
                    amount);
            mvTwos.add(mvTwo);

        }

        putRequestScope(AttributeConst.MENUS, menus);//取得した全メニューのデータ
        putRequestScope(AttributeConst.COOKINGSLS, cookingSlaves);//取得した料理のデータ
        putRequestScope(AttributeConst.TOPMATERILONES, mvOnes);
        putRequestScope(AttributeConst.TOPMATERILTWOS, mvTwos);
        //一覧画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}
