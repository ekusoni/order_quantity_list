package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.User;

/**
 *利用者データのDTOモデルの⇔Viewモデルの変換を行うクラス
 *
 */
public class UserConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param uv UserViewモデルのインスタンス
     * @return Userのインスタンス
     */
    public static User toModel(UserView uv) {

        return new User(
                uv.getId(),
                uv.getCode(),
                uv.getName(),
                uv.getPassword(),
                uv.getAuthorFlag()==null
                        ?null
                        :uv.getAuthorFlag()==AttributeConst.ROLE_AUTHOR.getIntegerValue()
                            ?JpaConst.ROLE_AUTHOR
                            :JpaConst.ROLE_VIEWER,
                uv.getDeleteFlag()==null
                        ?null
                        :uv.getDeleteFlag()==AttributeConst.DEL_FLAG_FALSE.getIntegerValue()
                            ?JpaConst.USE_DEL_TRUE
                            :JpaConst.USE_DEL_FALSE);



    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param u Userのインスタンス
     * @return UserViewのインスタンス
     */
    public static UserView toView(User u) {

        if(u==null) {
            return null;
        }


        return new UserView(
                u.getId(),
                u.getCode(),
                u.getName(),
                u.getPassword(),
                u.getAuthorFlag()==null
                    ?null
                    :u.getAuthorFlag()==JpaConst.ROLE_AUTHOR
                            ?AttributeConst.ROLE_AUTHOR.getIntegerValue()
                            :AttributeConst.ROLE_VIEWER.getIntegerValue(),
                u.getDeleteFlag()==null
                    ?null
                    :u.getDeleteFlag()==JpaConst.USE_DEL_TRUE
                            ?AttributeConst.DEL_FLAG_TURE.getIntegerValue()
                            :AttributeConst.DEL_FLAG_FALSE.getIntegerValue());
    }


    /**
     * DTOモデルのリストからviewモデルのリストを作成する
     * @param List DTOモデル
     * @return Viewモデル
     */
    public static List<UserView> toViewList(List<User> list){
        List<UserView> uvs=new ArrayList<>();

        for(User u: list) {
            uvs.add(toView(u));
        }

        return uvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param u DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(User u,UserView uv) {
        u.setId(uv.getId());
        u.setCode(uv.getCode());
        u.setName(uv.getName());
        u.setPassword(uv.getPassword());
        u.setAuthorFlag(uv.getAuthorFlag());
        u.setDeleteFlag(uv.getDeleteFlag());
    }


}