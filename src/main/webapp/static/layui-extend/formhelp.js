layui.define('jquery',function(exports){ //提示：模块也可以依赖其它模块，如：layui.define('layer', callback);
    var $ = layui.jquery;
    var MOD_NAME='formhelp';
    var formhelp = {
        config:{}
    };
    var thisHelp = function(){
        var that = this,options = that.config;

        return {
            setvalue: function(value){
                that.setvalue.call(that, value);
            }
            ,config: options
        }
    };
    var Class = function(options){
        var that = this;
        that.config = $.extend({}, that.config, formhelp.config, options);
        that.render();
    };
    //默认配置
    Class.prototype.config = {
        maxFiles: 5,//最多可以添加文件的数量
        fieldName: "file" ,//生成input时，name的名称
        canAddDel:true,//是否显示右上角删除文件图标
        showFileDesc:false,//是否显示文本框，让用户对文件进行描述
        data:[]//文件数据

    };
    Class.prototype.render = function(){
        var that = this,options = that.config;
        options.elem = $(options.elem);
        //开始插入替代元素
        var othis = options.elem;

        var fileContainer=$('<div class="myuploader-box"></div>');

        var data=options.data;
        var canAddDel=options.canAddDel;
        if (data.length>0){
            var iconClass="";
            var url="";
            var aTarget="";
            var rege=new RegExp('(jpg|png|gif|bmp|jpeg$)', 'i');
            for(var i=0;i<data.length;i++){
                var index="file_"+new Date().getTime();
                var item=$('<div class="myuploader-item"></div>');
                iconClass="layui-icon layui-icon-file";
                if (rege.test(data[i].ext)){
                    aTarget=' target="_blank" ';
                    iconClass="layui-icon layui-icon-picture-fine";
                }
                var icon=$('<div class="myuploader-icon"><i class="'+iconClass+'"></i></div>');
                var filename=$('<div class="myuploader-filename">'+data[i].originalName+'</div>');
                url=ctx+"/qmis/common/download?url="+data[i].url;
                var status=$('<div id="'+index+'" class="myuploader-status"><a '+aTarget+' href="'+url+'">点击下载查看</a></div>');
                var hidenfile=$('<input value="'+data[i].id+'" type="hidden" name="'+"old_"+options.fieldName+'" style="display: none;">');
                item.append(icon).append(filename).append(status).append(hidenfile);
                //是否允许删除
                if (canAddDel) {
                    var closeIcon=$('<div data-index="'+index+'" class="myuploader-close"><i class="layui-icon layui-icon-close-fill"></i></div>');
                    item.append(closeIcon);
                    closeIcon.on("click",function(){
                        $(this).parent().remove();
                    });
                }
                fileContainer.append(item);
            }
        }
        //是否显示添加按钮及操作
        if(canAddDel){
            var myAddBtn=$('<div class="myuploader-btn"><div><i class="layui-icon layui-icon-add-circle-fine"></i></div></div>');
            myAddBtn.on("click",function(){

                var num=fileContainer.children().length;
                if (num>=options.maxFiles){
                    layer.msg("最多选择"+options.maxFiles+"个文件");
                    return;
                }
                var index="file_"+new Date().getTime();
                var item=$('<div class="myuploader-item"></div>');
                var icon=$('<div class="myuploader-icon" title="点击选择"><i class="layui-icon layui-icon-file"></i></div>');
                var filename=$('<div class="myuploader-filename"></div>');
                var status=$('<div id="'+index+'" class="myuploader-status" style="background-color: #FF5722;">点击选择文件</div>');
                var descInput;
                if(options.showFileDesc){
                    descInput=$('<input type="text" name="'+options.fieldName+'Desc" placeholder="描述信息" style="height:22px;width:78px;font-size:12px;">');
                }
                var hidenfile=$('<input type="file" name="'+options.fieldName+'" style="display: none;">');
                var closeIcon=$('<div data-index="'+index+'" class="myuploader-close"><i class="layui-icon layui-icon-close-fill"></i></div>');
                item.append(icon).append(filename).append(status);
                if (options.showFileDesc){
                    item.append(descInput);
                }
                item.append(hidenfile).append(closeIcon);
                fileContainer.append(item);
                ////监听
                status.on("click",function () {
                    hidenfile.click();
                });
                hidenfile.on("change",function (e) {
                    var name=e.target.value;
                    name=name.substr(name.lastIndexOf('\\')+1);
                    filename.html(name);
                });
                closeIcon.on("click",function(){
                    $(this).parent().remove();
                });
            });
        }
        //最后添加大用户定义的容器里面
        othis.append(fileContainer);
        if(canAddDel){
            othis.append(myAddBtn);
        }

    };
    Class.prototype.setvalue = function(value){
        var that = this,options = that.config ;

        options.value = value ;
        that.render();
    };
    //核心入口
    formhelp.render = function(options){
        var inst = new Class(options);
        return thisHelp.call(inst);
    };
    exports(MOD_NAME, formhelp);
});

