//遮罩层
$(document).ready(function(){
	$('#load').hide();
});
//显示遮罩层
function updateResult(){
	$('#load').fadeIn(50);
}
//隐藏遮罩层
function hideResult(){
	$('#load').fadeOut(100);
}
//键盘监听事件
$(document).keypress(function(event){
    var code = event.keyCode;
    switch(code) {
    	//回车
        case 13:
            $("#query").click();
            break;
		//ctrl+enter 重置
        case 10:
            $("#reset").click();
            break;
        default:
            break;
    }
});