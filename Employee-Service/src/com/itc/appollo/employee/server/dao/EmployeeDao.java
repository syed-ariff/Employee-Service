package com.itc.appollo.employee.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.itc.appollo.employee.server.domain.EmployeeDto;

public class EmployeeDao extends BaseDao {

	@Override
	public Long createObject(Object object) {
		return (long) createEmployee((EmployeeDto) object);
	}

	@Override
	public List<Object> retrieveObjects(int maxResults, int firstResult) {
		EntityManager em = createEntityManager();
		List<Object> list = null;

		try {
			TypedQuery<Object> query = em.createQuery(
					"select a from Employee a", Object.class);
			query.setMaxResults(maxResults);
			query.setFirstResult(firstResult);
			list = query.getResultList();
		} finally {
			em.close();
		}

		return list;
	}

	public int createEmployee(EmployeeDto employeeDto) {

		// For an application-managed entity manager its best practice to create
		// a
		// new entity manager inside a method and close it before the method is
		// finished.

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();
		int employeeId = 1;

		try {
			tx.begin();
			em.persist(employeeDto);
			employeeId = employeeDto.getId();
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		return employeeId;
	}

	public EmployeeDto retrieveEmployee(Long employeeId) {

		EntityManager em = createEntityManager();
		EmployeeDto EmployeeDto = null;

		try {
			TypedQuery<EmployeeDto> query = em.createQuery(
					"select a from Employee a where a.id = ?1", EmployeeDto.class);
			query.setParameter(1, employeeId);
			EmployeeDto = query.getSingleResult();
		} finally {
			em.close();
		}

		return EmployeeDto;
	}

	public List<EmployeeDto> retrieveEmployees(int maxResults, int firstResult) {

		EntityManager em = createEntityManager();
		List<EmployeeDto> list = null;

		try {
			TypedQuery<EmployeeDto> query = em.createQuery(
					"select a from EmployeeDto a", EmployeeDto.class);
			query.setMaxResults(maxResults);
			query.setFirstResult(firstResult);
			list = query.getResultList();
			System.out.println("inside DAO--->"+list.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			em.close();
		}

		return list;
	}

	public EmployeeDto updateEmployee(EmployeeDto EmployeeDto) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();
		EmployeeDto Employee2 = null;

		try {
			tx.begin();
			Employee2 = em.merge(EmployeeDto);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		return Employee2;
	}

	public void deleteEmployee(EmployeeDto employeeDto) {

		EntityManager em = createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.merge(employeeDto));
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
