/*
 * SPDX-License-Identifier: Apache-2.0
 * SPDX-FileCopyrightText: Huawei Inc.
 *
 */

package org.eclipse.xpanse.modules.engine.terraform.exceptions;

/**
 * Defines possible exceptions returned by Terraform execution.
 */
public class ExecutorException extends RuntimeException {

    public ExecutorException() {
        super("TfExecutor Exception");
    }

    public ExecutorException(String message) {
        super("TfExecutor Exception:" + message);
    }

    public ExecutorException(String message, Throwable ex) {
        super(message, ex);
    }

    /**
     * Exception thrown.
     *
     * @param cmd    command that was executed in Terraform.
     * @param output Output of the command execution.
     * @param ex     Type of the exception thrown.
     */
    public ExecutorException(String cmd, String output, Throwable ex) {
        super("Executor Exception:\n"
                        + "\n** Cmd:\n" + cmd + "\n** Output:\n" + output,
                ex);
    }

    public ExecutorException(String cmd, String output) {
        super("Executor Exception:\n"
                + "\n** Cmd:\n" + cmd + "\n** Output:\n" + output);
    }
}
