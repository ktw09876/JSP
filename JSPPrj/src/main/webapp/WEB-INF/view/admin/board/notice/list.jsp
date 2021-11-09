<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- uri대신 c를 사용하겠다  -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>

<head>
	<title>코딩 전문가를 만들기 위한 온라인 강의 시스템</title>
	<meta charset="UTF-8">
	<title>공지사항목록</title>

	<link href="/css/customer/layout.css" type="text/css" rel="stylesheet" />
	<style>
		#visual .content-container {
			height: inherit;
			display: flex;
			align-items: center;

			background: url("/images/mypage/visual.png") no-repeat center;
		}
	</style>
</head>

<body>
	<!-- header 부분 -->

	<header id="header">

		<div class="content-container">
			<!-- ---------------------------<header>--------------------------------------- -->

			<h1 id="logo">
				<a href="/index.html">
					<img src="/images/logo.png" alt="뉴렉처 온라인" />

				</a>
			</h1>

			<section>
				<h1 class="hidden">헤더</h1>

				<nav id="main-menu">
					<h1>메인메뉴</h1>
					<ul>
						<li><a href="/guide">학습가이드</a></li>

						<li><a href="/course">강좌선택</a></li>
						<li><a href="/answeris/index">AnswerIs</a></li>
					</ul>
				</nav>

				<div class="sub-menu">

					<section id="search-form">
						<h1>강좌검색 폼</h1>
						<form action="/course">
							<fieldset>
								<legend>과정검색필드</legend>
								<label>과정검색</label>
								<input type="text" name="q" value="" />
								<input type="submit" value="검색" />
							</fieldset>
						</form>
					</section>

					<nav id="acount-menu">
						<h1 class="hidden">회원메뉴</h1>
						<ul>
							<li><a href="/index.html">HOME</a></li>
							<li><a href="/member/login.html">로그인</a></li>
							<li><a href="/member/agree.html">회원가입</a></li>
						</ul>
					</nav>

					<nav id="member-menu" class="linear-layout">
						<h1 class="hidden">고객메뉴</h1>
						<ul class="linear-layout">
							<li><a href="/member/home"><img src="/images/txt-mypage.png" alt="마이페이지" /></a></li>
							<li><a href="/notice/list.html"><img src="/images/txt-customer.png" alt="고객센터" /></a></li>
						</ul>
					</nav>

				</div>
			</section>

		</div>

	</header>

	<!-- --------------------------- <visual> --------------------------------------- -->
	<!-- visual 부분 -->

	<div id="visual">
		<div class="content-container"></div>
	</div>
	<!-- --------------------------- <body> --------------------------------------- -->
	<div id="body">
		<div class="content-container clearfix">

			<!-- --------------------------- aside --------------------------------------- -->
			<!-- aside 부분 -->


			<aside class="aside">
				<h1>ADMIN PAGE</h1>

				<nav class="menu text-menu first margin-top">
					<h1>마이페이지</h1>
					<ul>
						<li><a href="/admin/index.html">관리자홈</a></li>
						<li><a href="/teacher/index.html">선생님페이지</a></li>
						<li><a href="/student/index.html">수강생페이지</a></li>
					</ul>
				</nav>

				<nav class="menu text-menu">
					<h1>알림관리</h1>
					<ul>
						<li><a href="/admin/board/notice/list.html">공지사항</a></li>
					</ul>
				</nav>

			</aside>
			<!-- --------------------------- main --------------------------------------- -->



			<main class="main">
				<h2 class="main title">공지사항</h2>

				<div class="breadcrumb">
					<h3 class="hidden">경로</h3>
					<ul>
						<li>home</li>
						<li>고객센터</li>
						<li>공지사항</li>
					</ul>
				</div>

				<div class="search-form margin-top first align-right">
					<h3 class="hidden">공지사항 검색폼</h3>
					<form class="table-form">
						<fieldset>
							<legend class="hidden">공지사항 검색 필드</legend>
							<label class="hidden">검색분류</label>
							<select name="f">
								<option value="title">제목</option>
								<option value="writerId">작성자</option>
							</select>
							<label class="hidden">검색어</label>
							<input type="text" name="q" value="" />
							<input class="btn btn-search" type="submit" value="검색" />
						</fieldset>
					</form>
				</div>

				<form action="list" method="post">
					<div class="notice margin-top">
						<h3 class="hidden">공지사항 목록</h3>
						<table class="table">
							<thead>
								<tr>
									<th class="w60">번호</th>
									<th class="expand">제목</th>
									<th class="w100">작성자</th>
									<th class="w100">작성일</th>
									<th class="w60">조회수</th>
									<th class="w40">공개</th>
									<th class="w40">삭제</th>
								</tr>
							</thead>
							<tbody>
							
								<c:forEach var="n" items="${list }" > <!-- 'n'이라는 키워드로 pageContext저장소에 담아주는 역할, 
																								저장소에 담겨있는 데이터를 items에 담는다,
																								begin="1" end="3": 인덱스번호 1번부터 3번까지 -->
								<tr>
									<td>${n.id }</td>
									<td class="title indent text-align-left"><a href="detail?id=${n.id }">${n.title }</a><span>[${n.cmtCount }]</span></td>
									<td>${n.writerId }</td>
									<td>${n.regdate }</td>
									<td>${n.hit }</td>
									<td><input type="checkbox" name="open-id" value="${n.id }"></td>
									<td><input type="checkbox" name="del-id" value="${n.id }"></td>
								</tr>
								</c:forEach>
	
							</tbody>
						</table>
					</div>
	
					<c:set var="page" value="${(empty param.p)?1:param.p }" /> <!-- param.p가 비어있으면 1, 아니면 param.p를 채우겠다 -->
					<c:set var="startNum" value="${page-(page-1)%5 }" /> <!-- value에 있는 계산식이 연산되서 var에 있는 변수에 담김 -->
					<c:set var="lastNum" value="${fn:substringBefore(Math.ceil(count/10), '.')}" /> <!-- 11.0에서 '.'이 발견되면 그 앞에것만 출력 -->
					
					<div class="indexer margin-top align-right">
						<h3 class="hidden">현재 페이지</h3>
						<div><span class="text-orange text-strong">${(empty param.p) ? 1 : param.p }</span> / ${lastNum } pages</div> <!-- param.p가 비어있으면 1, 아니면 param.p를 채우겠다 -->
					</div>
	
					<div class="text-align-right margin-top">
						<input type="submit" class="btn-text btn-default" name="cmd" value="일괄공개">
						<input type="submit" class="btn-text btn-default" name="cmd" value="일괄삭제">
						<a class="btn-text btn-default" href="reg">글쓰기</a>				
					</div>
				</form>

				<div class="margin-top align-center pager">	
		
				<div>
					
					<c:if test="${startNum > 1 }">
						<a class="btn btn-prev" href="?p=${startNum-1 }&t=&q=" >이전</a>
					</c:if>
					<c:if test="${startNum <= 1 }">
						<span class="btn btn-prev" onclick="alert('이전 페이지가 없습니다.');">이전</span>
					</c:if>
				</div>
				
				<ul class="-list- center">
					<c:forEach var="i" begin="0" end="4">
						<c:if test="${(startNum+i) <= lastNum }">
							<li><a class="-text-  ${(page == (startNum+i)) ? 'orange' : '' } bold" href="?p=${startNum+i }&f=${param.f }&q=${param.q}" >${startNum+i }</a></li> <!-- EL안에서는 홑따옴표를 사용해도 괜찮다. -->
						</c:if>
					</c:forEach>
				</ul>
				<div>
					<c:if test="${startNum+4 < lastNum }">
						<a href="?p=${startNum+5 }&t=&q=" class="btn btn-next" >다음</a>
					</c:if>
					<c:if test="${startNum+4 >= lastNum }">
						<span class="btn btn-next" onclick="alert('다음 페이지가 없습니다.');">다음</span>
					</c:if>
				</div>
	
			</div>
			</main>


		</div>
	</div>

	<!-- ------------------- <footer> --------------------------------------- -->



	<footer id="footer">
		<div class="content-container">
			<h2 id="footer-logo"><img src="/images/logo-footer.png" alt="회사정보"></h2>

			<div id="company-info">
				<dl>
					<dt>주소:</dt>
					<dd>서울특별시 </dd>
					<dt>관리자메일:</dt>
					<dd>admin@newlecture.com</dd>
				</dl>
				<dl>
					<dt>사업자 등록번호:</dt>
					<dd>111-11-11111</dd>
					<dt>통신 판매업:</dt>
					<dd>신고제 1111 호</dd>
				</dl>
				<dl>
					<dt>상호:</dt>
					<dd>뉴렉처</dd>
					<dt>대표:</dt>
					<dd>홍길동</dd>
					<dt>전화번호:</dt>
					<dd>111-1111-1111</dd>
				</dl>
				<div id="copyright" class="margin-top">Copyright ⓒ newlecture.com 2012-2014 All Right Reserved.
					Contact admin@newlecture.com for more information</div>
			</div>
		</div>
	</footer>
</body>

</html>