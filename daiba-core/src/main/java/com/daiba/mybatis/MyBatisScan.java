package com.daiba.mybatis;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 　 　　   へ　　　 　／|
 * 　　    /＼7　　　 ∠＿/
 * 　     /　│　　 ／　／
 * 　    │　Z ＿,＜　／　　   /`ヽ
 * 　    │　　　 　　ヽ　    /　　〉
 * 　     Y　　　　　   `　  /　　/
 * 　    ｲ●　､　●　　⊂⊃〈　　/
 * 　    ()　 へ　　　　|　＼〈
 * 　　    >ｰ ､_　 ィ　 │ ／／      去吧！
 * 　     / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　     ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　    7　　　　　　　|／
 * 　　    ＞―r￣￣`ｰ―＿
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * @author　 penghaitao
 * @date　 2016-09-02  22:35
 * @description　 mybatis关系映射框架
 */
@Retention(RetentionPolicy.RUNTIME) //表示jvm运行时，此注解可被读出
@Target({ElementType.TYPE})         //表示注解MyBatisScan只能用来修饰类、接口、枚举类型
@Documented                         //注解表明这个注解应该被 javadoc工具记录
@Component                          //泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。　　－－－　tiny_dolphin
public @interface MyBatisScan {
    String value() default "";
}
