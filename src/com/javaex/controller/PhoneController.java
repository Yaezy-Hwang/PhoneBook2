package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhoneController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("./pbc --> doGet()");
		
		String action = request.getParameter("action");
		
		if("list".equals(action)) { //리스트
			System.out.println("list");
			
			PhoneDao dao = new PhoneDao();
			List<PersonVo> pList = dao.getPersonList();
			
			//포워드 리퀘스트에 값 넣기
			request.setAttribute("personList", pList);
			
			//forword 하는 방법
			WebUtil.forward(request, response, "/WEB-INF/list.jsp");

		} else if("wform".equals(action)) {
			System.out.println("writeform");
			
			//forword 하는 방법
			WebUtil.forward(request, response, "/WEB-INF/writeForm.jsp");
			
		} else if("insert".equals(action)) {
			System.out.println("insert");
			
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			PersonVo vo = new PersonVo(name, hp, company);
			PhoneDao dao = new PhoneDao();
			dao.personInsert(vo);
			
			WebUtil.redirect(response, "/pb2/pbc?action=list");
			
		} else if("uform".equals(action)) {
			System.out.println("uform"); //test
			
			int personId = Integer.parseInt(request.getParameter("personid")); //파라미터 가져오기
			
			PhoneDao phoneDao = new PhoneDao();
			PersonVo vo = phoneDao.getPerson(personId); //해당 id를 가진 사람 불러오기
				 
			//포워드 리퀘스트에 값 넣기
			request.setAttribute("personvo", vo);
			
			//forword 하는 방법
			WebUtil.forward(request, response, "/WEB-INF/updateForm.jsp");
			
		} else if("update".equals(action)) {
			System.out.println("update");
			
			int personId = Integer.parseInt(request.getParameter("personid"));
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			PersonVo vo = new PersonVo(personId,name,hp,company);
			PhoneDao dao = new PhoneDao();
			dao.personUpdate(vo);
			
			WebUtil.redirect(response, "/pb2/pbc?action=list");
			
		} else if("delete".equals(action)) {
			int personId = Integer.parseInt(request.getParameter("personid"));
			PhoneDao phoneDao = new PhoneDao();
			phoneDao.personDelete(personId);
			
			WebUtil.redirect(response, "/pb2/pbc?action=list");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
