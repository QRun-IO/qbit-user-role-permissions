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


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.kingsrook.qbits.userrolepermissions.utils.PermissionManager;
import com.kingsrook.qqq.backend.core.actions.customizers.TableCustomizerInterface;
import com.kingsrook.qqq.backend.core.exceptions.QException;
import com.kingsrook.qqq.backend.core.model.actions.tables.delete.DeleteInput;
import com.kingsrook.qqq.backend.core.model.actions.tables.insert.InsertInput;
import com.kingsrook.qqq.backend.core.model.actions.tables.update.UpdateInput;
import com.kingsrook.qqq.backend.core.model.data.QRecord;
import com.kingsrook.qqq.backend.core.utils.CollectionUtils;


/*******************************************************************************
 **
 *******************************************************************************/
public class UserRoleIntCustomizer implements TableCustomizerInterface
{

   /***************************************************************************
    **
    ***************************************************************************/
   @Override
   public List<QRecord> postInsert(InsertInput insertInput, List<QRecord> records) throws QException
   {
      Set<Integer> userIds = CollectionUtils.nonNullList(records).stream().map(r -> r.getValueInteger("userId")).collect(Collectors.toSet());
      PermissionManager.getInstance().flushCacheForUpdatedUserIds(userIds);
      return (records);
   }



   /***************************************************************************
    **
    ***************************************************************************/
   @Override
   public List<QRecord> postUpdate(UpdateInput updateInput, List<QRecord> records, Optional<List<QRecord>> oldRecordList) throws QException
   {
      Set<Integer> userIds = CollectionUtils.nonNullList(records).stream().map(r -> r.getValueInteger("userId")).collect(Collectors.toSet());
      if(oldRecordList.isPresent())
      {
         userIds = new HashSet<>(userIds);
         userIds.addAll(oldRecordList.get().stream().map(r -> r.getValueInteger("userId")).collect(Collectors.toSet()));
      }
      PermissionManager.getInstance().flushCacheForUpdatedUserIds(userIds);
      return (records);
   }



   /***************************************************************************
    **
    ***************************************************************************/
   @Override
   public List<QRecord> postDelete(DeleteInput deleteInput, List<QRecord> records) throws QException
   {
      Set<Integer> userIds = CollectionUtils.nonNullList(records).stream().map(r -> r.getValueInteger("userId")).collect(Collectors.toSet());
      PermissionManager.getInstance().flushCacheForUpdatedUserIds(userIds);
      return (records);
   }

}
