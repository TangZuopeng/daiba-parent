var basePath = (function() {
    var url = window.location + "";
    var h = url.split("//");
    var x = h[1].split("/");
    return h[0] + "//" + window.location.host + "/" + x[1];
})();

var imgPath = 'http://123.207.149.13';
// var imgPath = '';

//  页面重定向
function redirect(url) {
    window.location.href = basePath + '/WeiXin' + url;
}

//  自定义touch事件
function click(mTarget, callback){
    // mTarget.addEventListener('touchmove',function(event) {
    //     event.preventDefault();
    // });
    // mTarget.addEventListener('touchend',function(event) {
    //     var touch = event.changedTouches[0];
    //     var newTarget = document.elementFromPoint(touch.clientX, touch.clientY);
    //     if(newTarget == event.target){
    //         callback();
    //     }
    // });
}
