<#import "/spring.ftl" as spring />
<#include "navigation.ftl"/>
<#macro header>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Spring MVC prototype application">
    <meta name="author" content="drmanalo@nowhere.net">
    <link rel="icon" href="static/img/favicon.ico">

    <title>Spring MVC | Prototype</title>

    <!-- CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/css/cover.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
 <div class="site-wrapper">
  <div class="site-wrapper-inner">
   <div class="cover-container">
    <@navigation/>
</#macro>