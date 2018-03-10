package com.thinkjoy.common.util;

/**
 * Created by xufei on 2017/12/6.
 */
public class DictUtil {
    /**
     * 性别
     */
    public static enum sex{
        MAN("男","0"),WOMAN("女","1");

        private String name ;
        private String index ;

        sex(String name, String index) {
            this.name=name;
            this.index=index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }
    }

    /**
     * boolean 是否
     */
    public static enum BooleanEnmu{
        YES("是",true),NO("否",false);

        private String name;
        private Boolean index;
        BooleanEnmu(String name, Boolean index) {
            this.name=name;
            this.index=index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getIndex() {
            return index;
        }

        public void setIndex(Boolean index) {
            this.index = index;
        }
    }

    public static void main(String[] args){
        System.out.println(sex.MAN.getName());
    }
}
