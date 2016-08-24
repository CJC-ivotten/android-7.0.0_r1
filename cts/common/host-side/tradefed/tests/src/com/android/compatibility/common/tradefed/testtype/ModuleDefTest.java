/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.compatibility.common.tradefed.testtype;

import com.android.compatibility.common.tradefed.util.NoOpTestInvocationListener;
import com.android.compatibility.common.util.AbiUtils;
import com.android.tradefed.device.DeviceNotAvailableException;
import com.android.tradefed.result.ITestInvocationListener;
import com.android.tradefed.targetprep.ITargetPreparer;
import com.android.tradefed.testtype.IAbi;
import com.android.tradefed.testtype.IAbiReceiver;
import com.android.tradefed.testtype.IRemoteTest;
import com.android.tradefed.testtype.IRuntimeHintProvider;
import com.android.tradefed.testtype.ITestCollector;
import com.android.tradefed.testtype.ITestFilterReceiver;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleDefTest extends TestCase {

    private static final String NAME = "ModuleName";
    private static final String ABI = "mips64";
    private static final String ID = AbiUtils.createId(ABI, NAME);
    private static final String CLASS = "android.test.FoorBar";
    private static final String METHOD_1 = "testBlah1";
    private static final String TEST_1 = String.format("%s#%s", CLASS, METHOD_1);

    public void testAccessors() throws Exception {
        IAbi abi = new Abi(ABI, "");
        MockRemoteTest mockTest = new MockRemoteTest();
        IModuleDef def = new ModuleDef(NAME, abi, mockTest, new ArrayList<ITargetPreparer>());
        assertEquals("Incorrect ID", ID, def.getId());
        assertEquals("Incorrect ABI", ABI, def.getAbi().getName());
        assertEquals("Incorrect Name", NAME, def.getName());
    }

    private class MockRemoteTest implements IRemoteTest, ITestFilterReceiver, IAbiReceiver,
            IRuntimeHintProvider, ITestCollector {

        private final List<String> mIncludeFilters = new ArrayList<>();
        private final List<String> mExcludeFilters = new ArrayList<>();

        @Override
        public void addIncludeFilter(String filter) {
            mIncludeFilters.add(filter);
        }

        @Override
        public void addAllIncludeFilters(Set<String> filters) {
            mIncludeFilters.addAll(filters);
        }

        @Override
        public void addExcludeFilter(String filter) {
            mExcludeFilters.add(filter);
        }

        @Override
        public void addAllExcludeFilters(Set<String> filters) {
            mExcludeFilters.addAll(filters);
        }

        @Override
        public void run(ITestInvocationListener listener) throws DeviceNotAvailableException {
            // Do nothing
        }

        @Override
        public void setAbi(IAbi abi) {
            // Do nothing
        }

        @Override
        public long getRuntimeHint() {
            return 1L;
        }

        @Override
        public void setCollectTestsOnly(boolean shouldCollectTest) {
            // Do nothing
        }
    }

    private class MockListener extends NoOpTestInvocationListener {}
}
