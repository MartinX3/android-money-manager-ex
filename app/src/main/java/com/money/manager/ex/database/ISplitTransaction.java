/*
 * Copyright (C) 2012-2018 The Android Money Manager Ex Project Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.money.manager.ex.database;

import com.money.manager.ex.core.TransactionTypes;
import com.money.manager.ex.datalayer.IEntity;

import info.javaperformance.money.Money;

/**
 * Common interface for split transactions and recurring splits.
 */
public interface ISplitTransaction
        extends IEntity {

    Integer getId();

    void setId(int splitTransId);

    boolean hasId();

    Integer getAccountId();

    void setAccountId(int value);

    Money getAmount();

    void setAmount(Money splitTransAmount);

    String getNotes();

    void setNotes(String value);

    Integer getCategoryId();

    void setCategoryId(int categoryId);

    TransactionTypes getTransactionType(TransactionTypes parentTransactionType);

    void setTransactionType(TransactionTypes value, TransactionTypes parentTransactionType);
}
