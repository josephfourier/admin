<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title></title>
	
	<style>
		html,body{font-family: "宋体";}
		*{margin: 0;padding: 0;}
		.wrap {
			width:800px;
			position: relative;
			margin: 0 auto;
			background: url(${basePath}/resources/thinkjoy-admin/images/bg.png) 0 0 no-repeat;
			padding-bottom:200px;
		}

		.content {
			margin: 0 auto;
			padding: 270px 80px 0 80px;
		}
		
		.print {
			border:none;
			border-bottom: 1px solid #444;
			outline: none;
			text-align: center;
			min-width: 150px;
			font-size: 16px;
			font-weight: bold;
			font-family: "微软雅黑";
		}
		.print.no{
			border: 0;
			text-align: right;
			font-weight: 400;
		}
		.print.lg{
			width:280px;
		}
		.line{position: relative;}
		.line .number{
			padding-top: 20px;
			font-family: "微软雅黑";
			font-weight: 500;
		}
		.line span{
			line-height: 44px;
		}
		.line .fr{position: absolute;right:20px;top:30px;text-align: right;}
		
		.guides{
			margin-top:80px;
		}
		.guides .title{
			text-align: center;
			font-weight: bold;
			font-size: 18px;
		}
		.guides .info{
			font-size: 12px;
			padding: 10px 80px;
		}
		.guides .info p{
			line-height:18px;
		}
		.guides .info .info-item{padding-bottom: 15px;}
	</style>
</head>
	<body>
		<div class="wrap" id="wrap">
			<div class="content">
				<div class="line">
					<input type="text" class="print" value="${param.xuesheng}"/>
					<span>同学</span>
				</div>				
				<div class="line">
					<span>经我校招生办公室审核批准，你已被我校</span>
					<input type="text" class="print"  value="${param.xueyuan}" /><span>院系</span>
					<input type="text" class="print" value="${param.zhuanye}" /><span>专业取录，</span>
					<input type="text" class="print" value="${param.xuezhi}" /><span>学制，请持本通知书于</span>
					<input type="text" class="print lg" value="${param.baodaoriqi}" /><span>来校报到。</span>
				</div>
				<div class="line">
					<div class="number">NO.10690262524356272</div>
					<div class="fr">
						<div class="faculty"><input type="text" class="print no" value="建筑系"/></div>
						<div class="time"><input type="text" class="print no" value="2017.07.20"/></div>
					</div>
				</div>
			</div>
			
			<div class="guides" id="guides">
				<div class="title">新生须知</div>
				<div class="info">
					<div class="info-item">
						<p>一、新生请按通知书规定时间持身份证、录取通知书、高考准考证及其他有关证件来校报到。因故不能按期报到者，应向学院招生办公室请假，请假不得超过两周。未经请假及请假超过时限不报到者，视为放弃入学资格。</p>
					</div>
					<div class="info-item">
						<p>二、新生高考录取档案的提取，由新生个人持录取通知书到所在市、县（区）招办办理，在入校报到时交辅导员老师（部分省份由省招办直接邮寄的除外）。</p>
					</div>
					<div class="info-item">
						<p>三、新生自行转接党、团组织关系。新生中的党员，须持原所在县、旗、市委及以上各级党委组织部门出具的“中国共产党党员组织关系介绍信”（台头写“中共陕西省委高教工委”，去往单位为“西安外事学院”），到学校党委组织部接转组织关系；其入党档案材料由原所在党组织密封，交新生随身携带，在接转组织关系时，一并交学校党委组织部。新生中的团员持团员证转接关系，其档案资料可自带，或寄至新生被录取专业的二级学院团总支。</p>
					</div>
					<div class="info-item">
						<p>四、根据有关规定，新生入学时可自愿选择是否办理户口迁移手续，办理户口迁移时应注意以下几点：（1）户口迁移证入学时自带，迁移证必须为机打证，字迹清晰，手写无效。（2）户口迁移证与录取通知书上的姓名必须一致。（3）户口迁移证左侧须盖有骑缝章，签发日期处须盖有原户籍所在派出所户口专用章。（4）户口迁移证上迁往地址为“西安外事学院”。（5）西安市市区新生不迁移户口，农业户口学生根据本人意愿可就地办理“农转非”手续；郊县新生持户口本首页复印件及本人页原件办理落户。（6）将户口迁到学校的农业户口学生，毕业迁出时户口为非农业户口。</p>
					</div>
					
					<div class="info-item">
						<p>五、新生需带一寸同底版近期免冠彩色照片8张，背面用铅笔注明姓名及录取专业，开学报到后交予教学部门</p>
					</div>
				</div>
			</div>
		</div>
	
	</body>

</html>