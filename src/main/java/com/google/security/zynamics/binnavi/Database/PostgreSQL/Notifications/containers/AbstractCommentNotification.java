// Copyright 2011-2016 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.security.zynamics.binnavi.Database.PostgreSQL.Notifications.containers;

import com.google.common.base.Preconditions;
import com.google.security.zynamics.binnavi.Database.Exceptions.CouldntLoadDataException;
import com.google.security.zynamics.binnavi.Database.PostgreSQL.Notifications.interfaces.CommentNotification;
import com.google.security.zynamics.binnavi.Gui.GraphWindows.CommentDialogs.Interfaces.IComment;
import com.google.security.zynamics.binnavi.disassembly.CommentManager;
import com.google.security.zynamics.binnavi.disassembly.CommentManager.CommentOperation;

/**
 * This class implements the common methods for {@link CommentNotification comment notifications}.
 */
public abstract class AbstractCommentNotification implements CommentNotification {

  private final CommentOperation operation;
  private final Integer commentId;

  public AbstractCommentNotification(final CommentOperation operation, final Integer commentId) {
    this.operation =
        Preconditions.checkNotNull(operation, "Error: Operation argument can not be null.");
    this.commentId = commentId;
  }

  @Override
  public CommentOperation getOperation() {
    return operation;
  }

  @Override
  public Integer getCommentId() {
    return commentId;
  }

  abstract void informDelete(CommentManager manager, IComment comment);

  abstract void informAppend(CommentManager manager)
      throws CouldntLoadDataException;

  @Override
  public void inform(CommentManager manager) throws CouldntLoadDataException {
    final IComment comment = manager.getCommentById(getCommentId());
    if (operation == CommentOperation.DELETE && comment != null) {
      informDelete(manager, comment);
    } else if (operation == CommentOperation.APPEND) {
      informAppend(manager);
    }
  }
}
