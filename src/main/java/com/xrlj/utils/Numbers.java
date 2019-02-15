package com.xrlj.utils;

import java.math.BigDecimal;

/**
 * 数字工具类
 * Created by lujijiang on 2017/5/5.
 */
public class Numbers {
    /**
     * 大数字类
     */
    public static class BigNumber {

        private final static int DEFAULT_SCALE = 16;

        /**
         * 合计数
         */
        private BigDecimal total;

        /**
         * 构造一个大数字类
         * @param number
         */
        private BigNumber(Object number){
            this.total = Lang.toBigDecimal(number);
        }

        /**
         * 加法，可以有多个参数
         * @param args 要加的参数
         * @return
         */
        public BigNumber add(Object... args){
            for(Object arg:args){
                this.total = this.total.add(Lang.toBigDecimal(arg));
            }
            return this;
        }

        /**
         * 减法
         * @param arg 要减去的数
         * @return
         */
        public BigNumber sub(Object arg){
            this.total = this.total.subtract(Lang.toBigDecimal(arg));
            return this;
        }

        /**
         * 乘法
         * @param arg 要乘的数
         * @return
         */
        public BigNumber mul(Object arg){
            this.total = this.total.multiply(Lang.toBigDecimal(arg));
            return this;
        }

        /**
         * 除法
         * @param arg 要除的数
         * @param scale 结果精度
         * @return
         */
        public BigNumber div(Object arg, int scale){
            this.total = this.total.divide(Lang.toBigDecimal(arg),scale,BigDecimal.ROUND_HALF_UP);
            return this;
        }

        /**
         * 除法
         * @param arg 要除的数
         * @return
         */
        public BigNumber div(Object arg){
            return div(arg,DEFAULT_SCALE);
        }

        /**
         * 乘方
         * @param arg 乘方数
         * @return
         */
        public BigNumber pow(int arg){
            this.total = this.total.pow(arg);
            return this;
        }

        public BigNumber op(char op,Object arg){
            if(arg==null){
                throw new IllegalArgumentException("The arg should not be null");
            }
            switch (op){
                case '+':return this.add(arg);
                case '-':return this.sub(arg);
                case '*':return this.mul(arg);
                case '/':return this.div(arg);
                case '^':{
                    if(arg.getClass().equals(int.class)
                            ||arg.getClass().equals(Integer.class)
                            ||arg.getClass().equals(short.class)
                            ||arg.getClass().equals(Short.class)
                            ||arg.getClass().equals(byte.class)
                            ||arg.getClass().equals(Byte.class)
                            ){
                        return this.pow((int)arg);
                    }
                    else {
                        throw new IllegalArgumentException(String.format("UnSupport pow operation with class %s",arg.getClass().getCanonicalName()));
                    }
                }
                default:throw new IllegalArgumentException(String.format("UnSupport operation with '%c'",op));
            }
        }

        /**
         * 获取原生大数字
         * @return
         */
        public BigDecimal get(){
            return total;
        }

        @Override
        public String toString() {
            return total.toPlainString();
        }
    }

    public static BigNumber big(Object arg){
        return new BigNumber(arg);
    }
}
