/**
 * Created by tao on 2016/9/20.
 */

//  底部tab控制
var homeTab = $('#homeTab');
var orderTab = $('#orderTab');
var mineTab = $('#mineTab');

//  底部Tab控制
click(homeTab, function(event) {
    redirect('/main/home');
});

click(orderTab, function(event) {
    redirect('/main/order');
});

click(mineTab, function(event) {
    redirect('/main/mine');
});

function updateTabTarget(target,targetClass){

    jQuery(target.children("span:first")).css('color','#ffffff')
    jQuery(target.children("span:last")).css('color','#ffffff');
    jQuery(target.children("span:first")).removeClass();
    jQuery(target.children("span:first")).addClass(targetClass);
}