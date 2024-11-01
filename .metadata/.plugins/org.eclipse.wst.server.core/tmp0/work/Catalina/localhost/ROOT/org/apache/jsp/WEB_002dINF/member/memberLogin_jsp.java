/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.85
 * Generated at: 2024-10-31 07:06:21 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class memberLogin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSP들은 오직 GET, POST 또는 HEAD 메소드만을 허용합니다. Jasper는 OPTIONS 메소드 또한 허용합니다.");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("<!DOCTYPE HTML>\n");
      out.write("<HTML>\n");
      out.write("<HEAD>\n");
      out.write("<TITLE>로그인</TITLE>\n");
      out.write("<style>\n");
      out.write("#parent {\n");
      out.write("display : table;\n");
      out.write("padding : 10px;\n");
      out.write("background-color : darkgray;\n");
      out.write("margin-top : 30px; \n");
      out.write("}\n");
      out.write("#child {\n");
      out.write("display : table-cell;\n");
      out.write("text-align:center;\n");
      out.write("background-color : white;\n");
      out.write("}\n");
      out.write("ul{\n");
      out.write("	margin : 0px;\n");
      out.write("	padding : 0px;	\n");
      out.write("}\n");
      out.write("li {\n");
      out.write("	display : inline-block;\n");
      out.write("	list-style-type: none;\n");
      out.write("	padding : 0px 5px ;\n");
      out.write("	margin-top : 20px;\n");
      out.write("	margin-bottom : 10px;\n");
      out.write("	margin-left : 20px;\n");
      out.write("	margin-right : 20px;\n");
      out.write("	height: 20px;\n");
      out.write("}\n");
      out.write("\n");
      out.write("input[type=text]:focus,input[type=password]:focus{\n");
      out.write("	font-size: 120%;\n");
      out.write("}\n");
      out.write("\n");
      out.write("</style>\n");
      out.write("<script>\n");
      out.write("// 아이디, 비밀번호 유효성검사\n");
      out.write("function check(){\n");
      out.write("	\n");
      out.write("	let memberid = document.getElementsByName('memberid');\n");
      out.write("	let memberpwd = document.getElementsByName('memberpwd');\n");
      out.write("	//alert(memberid[0].value);\n");
      out.write("	//alert(memberpwd[0].value);\n");
      out.write("	\n");
      out.write("	if (memberid[0].value==\"\"){\n");
      out.write("		alert(\"아이디를 입력해주세요\");\n");
      out.write("		memberid[0].focus();\n");
      out.write("		return;\n");
      out.write("	} else if (memberpwd[0].value==\"\"){\n");
      out.write("		alert(\"비밀번호를 입력해주세요\");\n");
      out.write("		memberpwd[0].focus();\n");
      out.write("		return;\n");
      out.write("	}\n");
      out.write("	var fm = document.frm;\n");
      out.write("	fm.action =\"");
      out.print(request.getContextPath());
      out.write("/member/memberLoginAction.aws\"; //가상경로 지정 action은 처리하는 의미\n");
      out.write("	fm.method =\"post\";\n");
      out.write("	fm.submit();\n");
      out.write("	return;\n");
      out.write("}\n");
      out.write("\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("\n");
      out.write("</HEAD>\n");
      out.write(" <BODY>\n");
      out.write("<header>로그인</header>\n");
      out.write("<nav></nav>\n");
      out.write("<link href=\"../resources/css/style2.css\" type=\"text/css\" rel=\"stylesheet\">\n");
      out.write("\n");
      out.write("<section>\n");
      out.write("	<article>\n");
      out.write("	<div id=\"parent\">\n");
      out.write("	<div id=\"child\">\n");
      out.write("	<form name=\"frm\">\n");
      out.write("	<ul>\n");
      out.write("	<li>아이디</li>	\n");
      out.write("	<li><input type=\"text\" name=\"memberid\" maxlength=\"20\"></li>\n");
      out.write("	<li>비밀번호</li>\n");
      out.write("	<li><input type=\"password\" name=\"memberpwd\" maxlength=\"20\"></li>\n");
      out.write("	</ul>\n");
      out.write("	<ul>\n");
      out.write("	<li><input type=\"button\" name=\"btn\" value=\"로그인하기\" style=\"width:200px;\" onclick=\"check();\">\n");
      out.write("	</li>\n");
      out.write("	</ul>	\n");
      out.write("</form>\n");
      out.write("</div>\n");
      out.write("</div>\n");
      out.write("</article>	\n");
      out.write("</section>\n");
      out.write("<aside>\n");
      out.write("</aside>\n");
      out.write("<footer>\n");
      out.write("made by hji.\n");
      out.write("</footer>\n");
      out.write("</BODY>\n");
      out.write("</HTML>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
