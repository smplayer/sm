<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="zh-CN">
<head>
    <title>产品列表</title>
    <c:import url="/WEB-INF/views/jsp/common/common-meta.jsp"/>
    <c:import url="/WEB-INF/views/jsp/common/common-link.jsp"/>
    <link href="<c:url value='/resources/css/material/material.css'/>" rel="stylesheet">
</head>

<body class="root module layout-fixed-tmb">

<div class="module-top">
    <div class="module layout-lmr">
        <div class="module-left nav-back"></div>
        <div class="module-main">
            ${material.name}
        </div>
        <div class="module-right"></div>
    </div>
</div>
<div class="module-main">
    <div class="module layout-fixed-mb">
        <div class="module-main">

        </div>
        <div class="module-bottom">

        </div>
    </div>
</div>

<!-- bottombar -->
<div class="module-bottom">
    <c:import url="/WEB-INF/views/jsp/common/bottomNav.jsp"/>
</div>

</body>

<c:import url="/WEB-INF/views/jsp/common/common-script.jsp"/>
</html>
