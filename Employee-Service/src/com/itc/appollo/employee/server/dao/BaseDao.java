/**
 * (C) Copyright 2010, 2011 upTick Pty Ltd
 *
 * Licensed under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation. You may obtain a copy of the
 * License at: http://www.gnu.org/copyleft/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.itc.appollo.employee.server.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class BaseDao {

  public abstract Long createObject(Object object);

  public abstract List<Object> retrieveObjects(int maxResults, int firstResult);

  // <persistence-unit name="au.com.uptick.serendipity" transaction-type="RESOURCE_LOCAL">
  private static final EntityManagerFactory entityManagerFactory =
      Persistence.createEntityManagerFactory("com.itc.appollo.employee");

  public static EntityManager createEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

  // public static EntityManagerFactory get() {
  //   return entityManagerFactory;
  // }
}
