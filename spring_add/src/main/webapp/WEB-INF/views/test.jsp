<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>	
<title>Insert title here</title>
<style>
#modDiv {
	width: 300px;
	height: 100px;
	background-color: gray;
	position: absolute;
	top: 50%;
	left: 50%;
	margin-top: -50px;
	margin-left: -150px;
	padding: 10px;
	z-index: 1000;
}

.pagination {
  width: 100%;
}

.pagination li{
  list-style: none;
  float: left; 
  padding: 3px; 
  border: 1px solid blue;
  margin:3px;  
}

.pagination li a{
  margin: 3px;
  text-decoration: none;  
}

</style>
</head>
<body>
	<div id="modDiv" style="display:none;">
		<div class="modal-title"></div>
		<div>
			<input type="text" id="replytext">
		</div>
		<div>
			<button type="button" id="replyModBtn">Modify</button>
			<button type="button" id="replyDelBtn">Delete</button>
			<button type="button" id="closwBtn">Close</button>
		</div>
	</div>

	<h2>Ajax Test Page</h2>
	
	<div>
		<div>
			REPLYER <input type="text" name="replyer" id="newReplyWriter">
		</div>
		<div>
			REPLYER TEXT <input type="text" name="replytext" id="newReplytext">
		</div>
		<button id="replyAddBtn">ADD REPLY</button>
	</div>
	
	<ul id="replies"></ul>
	<ul class="pagination"></ul>

<script type="text/javascript">
	var bno = 62;
	
	
	function getAllList() {
		$.getJSON("/replies/all/"+bno, function(data) {
			console.log(data.length);
			var str = "";
			$(data).each(function() {
				str += "<li data-rno='" + this.rno+"'class='replyLi'>"
					+ this.rno+":"+this.replytext
					+"<button>MOD</button></li>";
			});
		
			$('#replies').html(str);
		});
	}
	
	$('#replyAddBtn').on('click', function() {
		var replyer = $('#newReplyWriter').val();
		var replytext = $('#newReplytext').val();
		
		$.ajax({
			type : 'post',
			url : '/replies',
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "POST"
			},
			dataType : 'text',
			data : JSON.stringify({		//JSON으로 변환하기위한 라이브러리 메소드
				bno : bno,
				replyer : replyer,
				replytext : replytext
			}),
			success : function(result) {
				if(result=="SUCCESS"){
					alert("등록 됬다.");
					//getAllList();
					getPageList(1);
				}
			}
		});
	});
	
	
	$('#replies').on('click', '.replyLi button', function() {
		var reply = $(this).parent();	//li
		var rno = reply.attr('data-rno');	
		var replytext=reply.text();
		
		$('.modal-title').html(rno);
		$('#replytext').val(replytext);
		$('#modDiv').show('show');
	});
	
	$('#replyModBtn').on('click', function() {
		var rno = $('.modal-title').html();
		var replytext = $('#replytext').val();
		
		$.ajax({
			type : 'patch',
			url : '/replies/'+rno,
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "PATCH"
			},
			data : JSON.stringify({replytext : replytext}),
			dataType : 'text',
			success : function(result) {
				if(result=='SUCCESS'){
					alert("수정완료");
					getAllList();
					$('#modDiv').hide('show');
				}
			}
		});
	});
	
	$('#replyDelBtn').on('click', function() {
		var rno = $('.modal-title').html();
		var replytext = $('#replytext').val();
		
		$.ajax({
			type : 'delete',
			url : '/replies/'+rno,
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "DELETE"
			},
			dataType : 'text',
			success : function(result) {
				if(result=='SUCCESS'){
					alert("삭제완료ㅋ");
					getAllList();
					$('#modDiv').hide('show');
				}
			}
		});
	});
	
	function getPageList(page){
		
		  $.getJSON("/replies/"+bno+"/"+page , function(data){
			  
			  console.log(data.list.length);
			  
			  var str ="";
			  
			  $(data.list).each(function(){
				  str+= "<li data-rno='"+this.rno+"' class='replyLi'>" 
				  +this.rno+":"+ this.replytext+
				  "<button>MOD</button></li>";
			  });
			  
			  $("#replies").html(str);
			  
			  printPaging(data.pageMaker);
			  
		  });
	  }		
		
		  
		function printPaging(pageMaker){
			
			var str = "";
			
			if(pageMaker.prev){
				str += "<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
			}
			
			for(var i=pageMaker.startPage, len = pageMaker.endPage; i <= len; i++){				
					var strClass= pageMaker.cri.page == i?'class=active':'';
				  str += "<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";
			}
			
			if(pageMaker.next){
				str += "<li><a href='"+(pageMaker.endPage + 1)+"'> >> </a></li>";
			}
			$('.pagination').html(str);				
		}
		
		var replyPage = 1;
		
		$('.pagination').on('click', 'li a', function(event) {
			event.preventDefault();
			replyPage = $(this).attr('href');
			getPageList(replyPage);
		});





</script>
</body>
</html>