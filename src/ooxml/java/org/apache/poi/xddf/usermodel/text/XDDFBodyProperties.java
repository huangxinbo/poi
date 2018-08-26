/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.xddf.usermodel.text;

import org.apache.poi.util.Beta;
import org.apache.poi.util.Internal;
import org.apache.poi.xddf.usermodel.XDDFExtensionList;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBodyProperties;

@Beta
public class XDDFBodyProperties {
    private CTTextBodyProperties props;

    @Internal
    protected XDDFBodyProperties(CTTextBodyProperties properties) {
        this.props = properties;
    }

    @Internal
    protected CTTextBodyProperties getXmlObject() {
        return props;
    }

    public XDDFAutoFit getAutoFit() {
        if (props.isSetNoAutofit()) {
            return new XDDFNoAutoFit(props.getNoAutofit());
        } else if (props.isSetNormAutofit()) {
            return new XDDFNormalAutoFit(props.getNormAutofit());
        } else if (props.isSetSpAutoFit()) {
            return new XDDFShapeAutoFit(props.getSpAutoFit());
        }
        return new XDDFNoAutoFit();
    }

    public void setAutoFit(XDDFAutoFit autofit) {
        props.unsetNoAutofit();
        props.unsetNormAutofit();
        props.unsetSpAutoFit();
        if (autofit instanceof XDDFNoAutoFit) {
            props.setNoAutofit(((XDDFNoAutoFit) autofit).getXmlObject());
        } else if (autofit instanceof XDDFNormalAutoFit) {
            props.setNormAutofit(((XDDFNormalAutoFit) autofit).getXmlObject());
        } else if (autofit instanceof XDDFShapeAutoFit) {
            props.setSpAutoFit(((XDDFShapeAutoFit) autofit).getXmlObject());
        }
    }

    public XDDFExtensionList getExtensionList() {
        if (props.isSetExtLst()) {
            return new XDDFExtensionList(props.getExtLst());
        } else {
            return null;
        }
    }

    public void setExtensionList(XDDFExtensionList list) {
        if (list == null) {
            props.unsetExtLst();
        } else {
            props.setExtLst(list.getXmlObject());
        }
    }
}
