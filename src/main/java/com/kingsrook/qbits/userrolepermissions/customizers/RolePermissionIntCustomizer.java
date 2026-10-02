/*
 * QQQ - Low-code Application Framework for Engineers.
 * Copyright (C) 2021-2025.  Kingsrook, LLC
 * 651 N Broad St Ste 205 # 6917 | Middletown DE 19709 | United States
 * contact@kingsrook.com
 * https://github.com/Kingsrook/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kingsrook.qbits.userrolepermissions.customizers;


import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.kingsrook.qbits.userrolepermissions.model.UserRoleInt;
import com.kingsrook.qbits.userrolepermissions.utils.PermissionManager;
import com.kingsrook.qqq.backend.core.actions.customizers.RecordCustomizerUtilityInterface;
import com.kingsrook.qqq.backend.core.actions.customizers.TableCustomizerInterface;
import com.kingsrook.qqq.backend.core.actions.tables.QueryAction;
import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.model.actions.tables.delete.DeleteInput;
import com.kingsrook.qqq.backend.core.model.actions.tables.insert.InsertInput;
import com.kingsrook.qqq.backend.core.model.actions.tables.query.QCriteriaOperator;
import com.kingsrook.qqq.backend.core.model.actions.tables.query.QFilterCriteria;
import com.kingsrook.qqq.backend.core.model.actions.tables.query.QQueryFilter;
import com.kingsrook.qqq.backend.core.model.actions.tables.update.UpdateInput;
import com.kingsrook.qqq.backend.core.model.data.QRecord;
import com.kingsrook.qqq.backend.core.utils.CollectionUtils;
import com.kingsrook.qqq.backend.core.utils.ValueUtils;


/*******************************************************************************
 **
 *******************************************************************************/
public class RolePermissionIntCustomizer implements TableCustomizerInterface
{

   /***************************************************************************
    **
    ***************************************************************************/
   @Override
   public List<QRecord> postInsert(InsertInput insertInput, List<QRecord> records) throws QException
   {
      Set<Integer> userIds = getUserIdsForRolePermissionIntRecords(records, Optional.empty());
      PermissionManager.getInstance().flushCacheForUpdatedUserIds(userIds);

      Set<Integer> roleIds = getRoleIdsForRolePermissionIntRecords(records, Optional.empty());
      PermissionManager.getInstance().flushCacheForUpdatedRoleIds(roleIds);

      return (records);
   }



   /***************************************************************************
    ** get all the roleIds from the rolePermissionInts in the input list -
    ** noting that, if we're doing updates, we can look in oldRecordMap for
    ** the values (e.g., if an update is only changing permissionId, not roleId) -
    ** then look up the users for those roles.
    ***************************************************************************/
   private static Set<Integer> getUserIdsForRolePermissionIntRecords(List<QRecord> records, Optional<Map<Serializable, QRecord>> oldRecordMap) throws QException
   {
      Set<Integer> roleIds = getRoleIdsForRolePermissionIntRecords(records, oldRecordMap);

      List<QRecord> userRoleInts = QueryAction.execute(UserRoleInt.TABLE_NAME, new QQueryFilter(new QFilterCriteria("roleId", QCriteriaOperator.IN, roleIds)));

      Set<Integer> userIds = userRoleInts.stream()
         .map(r -> r.getValueInteger("userId"))
         .collect(Collectors.toSet());

      return userIds;
   }



   /***************************************************************************
    ** get all the roleIds from the rolePermissionInts in the input list -
    ** noting that, if we're doing updates, we can look in oldRecordMap for
    ** the values (e.g., if an update is only changing permissionId, not roleId).
    ***************************************************************************/
   private static Set<Integer> getRoleIdsForRolePermissionIntRecords(List<QRecord> records, Optional<Map<Serializable, QRecord>> oldRecordMap) throws QException
   {
      Set<Integer> roleIds = CollectionUtils.nonNullList(records).stream()
         .map(r -> ValueUtils.getValueAsInteger(RecordCustomizerUtilityInterface.getValueFromRecordElseFromOldRecord("roleId", r, r.getValueInteger("id"), oldRecordMap)))
         .collect(Collectors.toSet());

      return (roleIds);
   }



   /***************************************************************************
    **
    ***************************************************************************/
   @Override
   public List<QRecord> postUpdate(UpdateInput updateInput, List<QRecord> records, Optional<List<QRecord>> oldRecordList) throws QException
   {
      Optional<Map<Serializable, QRecord>> oldRecordMap = oldRecordListToMap("id", oldRecordList);

      Set<Integer> userIds = getUserIdsForRolePermissionIntRecords(records, oldRecordMap);
      Set<Integer> roleIds = getRoleIdsForRolePermissionIntRecords(records, oldRecordMap);

      if(oldRecordList.isPresent())
      {
         userIds = new HashSet<>(userIds);
         userIds.addAll(getUserIdsForRolePermissionIntRecords(oldRecordList.get(), Optional.empty()));

         roleIds = new HashSet<>(roleIds);
         roleIds.addAll(getRoleIdsForRolePermissionIntRecords(oldRecordList.get(), Optional.empty()));
      }

      PermissionManager.getInstance().flushCacheForUpdatedUserIds(userIds);
      PermissionManager.getInstance().flushCacheForUpdatedRoleIds(roleIds);

      return (records);
   }



   /***************************************************************************
    **
    ***************************************************************************/
   @Override
   public List<QRecord> postDelete(DeleteInput deleteInput, List<QRecord> records) throws QException
   {
      Set<Integer> userIds = getUserIdsForRolePermissionIntRecords(records, Optional.empty());
      PermissionManager.getInstance().flushCacheForUpdatedUserIds(userIds);

      Set<Integer> roleIds = getRoleIdsForRolePermissionIntRecords(records, Optional.empty());
      PermissionManager.getInstance().flushCacheForUpdatedRoleIds(roleIds);

      return (records);
   }

}
