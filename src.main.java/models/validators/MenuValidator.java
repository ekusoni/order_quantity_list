package models.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import actions.views.MenuView;
import constants.MessageConst;

/**
 * メニューインスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class MenuValidator {


    /**
     * メニューインスタンスのバリデーションを行う
     * @param mv メニューインスタンス
     * @return エラー
     */
    public static List<String> validate(MenuView mv) {
        List<String> errors = new ArrayList<String>();

        String startEndError= validateStartEnd(mv.getStartDate(),mv.getEndDate());
        if(!startEndError.equals("")) {
            errors.add(startEndError);
        }

        return errors;




    }

    private static String validateStartEnd(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)) {
            return MessageConst.E_MENU_NODATE.getMessage();
        }

    return "";
    }







}
