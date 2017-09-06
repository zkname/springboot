<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp" %>
<table class="table table-hover">
  <tr>
    <td>
第${page.pageNo}页, 共${page.totalItems}条&nbsp;&nbsp;&nbsp;&nbsp;

			<a href="javascript:firstPage()" >第一页</a>
			<a href="javascript:prePage()" >上一页</a>
			
				Page
				<strong>${page.pageNo}</strong> / ${page.totalPages}
			
			<a href="javascript:nextPage()" >下一页</a>
			<a href="javascript:lastPage(${page.totalPages})" >最后一页</a>
			</td>
  </tr>
</table>
<script type="text/javascript">
	$(document).ready(function() {
		$("#pagechange").change(function() {
			$("#pageSize").val(this.value);
			$("#form").submit();
		});
	});
	function firstPage() {
		if(${page.pageNo}>1)
			jumpPage(1)
	}
	function prePage() {
		if(${page.pageNo}>1)
			jumpPage(${page.prePage})
	}
	function nextPage(){
		if(${page.pageNo}<${page.totalPages})
			jumpPage(${page.nextPage})
	}
	function lastPage(){
		if(${page.pageNo}<${page.totalPages})
			jumpPage(${page.totalPages})
	}
	function jumpPage(n){
		$("#pageNo").val(n);
		$("#form").submit();
	}
</script>