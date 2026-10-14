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

package com.kingsrook.qbits.userrolepermissions;


import java.util.List;
import com.kingsrook.qqq.backend.core.model.metadata.QInstance;
import com.kingsrook.qqq.backend.core.model.metadata.producers.MetaDataCustomizerInterface;
import com.kingsrook.qqq.backend.core.model.metadata.qbits.ProvidedOrSuppliedTableConfig;
import com.kingsrook.qqq.backend.core.model.metadata.qbits.QBitConfig;
import com.kingsrook.qqq.backend.core.model.metadata.tables.QTableMetaData;


/*******************************************************************************
 ** Configuration data for this qbit.
 **
 *******************************************************************************/
public class UserRolePermissionsQBitConfig implements QBitConfig
{
   private ProvidedOrSuppliedTableConfig               userTableConfig;
   private MetaDataCustomizerInterface<QTableMetaData> tableMetaDataCustomizer;



   /***************************************************************************
    **
    ***************************************************************************/
   @Override
   public void validate(QInstance qInstance, List<String> errors)
   {
      assertCondition(userTableConfig != null, "userTableConfig must be provided", errors);
   }



   /*******************************************************************************
    ** Getter for userTableConfig
    *******************************************************************************/
   public ProvidedOrSuppliedTableConfig getUserTableConfig()
   {
      return (this.userTableConfig);
   }



   /*******************************************************************************
    ** Setter for userTableConfig
    *******************************************************************************/
   public void setUserTableConfig(ProvidedOrSuppliedTableConfig userTableConfig)
   {
      this.userTableConfig = userTableConfig;
   }



   /*******************************************************************************
    ** Fluent setter for userTableConfig
    *******************************************************************************/
   public UserRolePermissionsQBitConfig withUserTableConfig(ProvidedOrSuppliedTableConfig userTableConfig)
   {
      this.userTableConfig = userTableConfig;
      return (this);
   }


   /*******************************************************************************
    ** Getter for tableMetaDataCustomizer
    *******************************************************************************/
   public MetaDataCustomizerInterface<QTableMetaData> getTableMetaDataCustomizer()
   {
      return (this.tableMetaDataCustomizer);
   }



   /*******************************************************************************
    ** Setter for tableMetaDataCustomizer
    *******************************************************************************/
   public void setTableMetaDataCustomizer(MetaDataCustomizerInterface<QTableMetaData> tableMetaDataCustomizer)
   {
      this.tableMetaDataCustomizer = tableMetaDataCustomizer;
   }



   /*******************************************************************************
    ** Fluent setter for tableMetaDataCustomizer
    *******************************************************************************/
   public UserRolePermissionsQBitConfig withTableMetaDataCustomizer(MetaDataCustomizerInterface<QTableMetaData> tableMetaDataCustomizer)
   {
      this.tableMetaDataCustomizer = tableMetaDataCustomizer;
      return (this);
   }


}
