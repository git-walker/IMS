<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<div style="padding:5px;">
    <form class="layui-form" action="" lay-filter="editGoodsForm" id="editGoodsForm" method="post">
        <div class="layui-form-item" >
            <label class="layui-form-label" >商品状态</label>
            <div class="layui-input-block">
                <input type="radio" name="status" value="0" title="在售" <c:if test="${goods.status==0}">checked</c:if>>
                <input type="radio" name="status" value="1"  title="停售"<c:if test="${goods.status==1}">checked</c:if>>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">商品名称</label>
                <div class="layui-input-inline">
                    <input type="text" id="goodsName" name="goodsName" value="${goods.goodsName}" lay-verify="required" autocomplete="off" placeholder="请输入商品名称" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">商品编码</label>
                <div class="layui-input-inline">
                    <input type="text" id="goodsCode" name="goodsCode" value="${goods.goodsCode}" lay-verify="required" autocomplete="off" placeholder="请输入商品编码" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">商品类型</label>
                <div class="layui-input-inline">
                    <input type="text" id="goodsType" name="goodsType" value="${goods.goodsType}" lay-verify="required" autocomplete="off" placeholder="请输入商品类型" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">商品品牌</label>
                <div class="layui-input-inline">
                    <select id="goodsBrand" name="goodsBrand" lay-verify="required" placeholder="请选择供应商"  lay-search>
                        <c:forEach items="${supplierList}" var="supplier">
                            <option value="${supplier.id}" <c:if test="${goods.goodsBrand eq supplier.id}">selected</c:if>>${supplier.fullName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">规格型号</label>
                <div class="layui-input-inline">
                    <input type="text" id="specification" name="specification" value="${goods.specification}" lay-verify="required" autocomplete="off" placeholder="请输入商品规格型号" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">预计进价</label>
                <div class="layui-input-inline">
                    <input type="text" id="buyPrice" name="buyPrice" value="${goods.buyPrice}" lay-verify="required" autocomplete="off" placeholder="请输入预计进价" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">预计售价</label>
                <div class="layui-input-inline">
                    <input type="text" id="salePrice" name="salePrice" value="${goods.salePrice}" lay-verify="required" autocomplete="off" placeholder="请输入预计售价" class="layui-input">
                </div>
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">图片上传</label>
            <div class="layui-input-block" id="imgAttach" name="imgAttach">
            </div>
        </div>
        <div class="layui-form-item layui-form-text">
            <label class="layui-form-label">备注</label>
            <div class="layui-input-block">
                <textarea id="remarks" name="remarks" placeholder="请输入内容" class="layui-textarea">${goods.remarks}</textarea>
            </div>
        </div>
        <c:if test="${!readonly}">
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">保存</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
        </c:if>
        <input type="hidden" name="id" value="${goods.id}">
    </form>
</div>
