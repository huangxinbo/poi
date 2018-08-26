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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.util.Beta;
import org.apache.poi.util.Internal;
import org.apache.poi.util.Units;
import org.apache.poi.xddf.usermodel.XDDFExtensionList;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraphProperties;

@Beta
public class XDDFParagraphProperties {
    private CTTextParagraphProperties props;
    private XDDFParagraphBulletProperties bullet;

    @Internal
    protected XDDFParagraphProperties(CTTextParagraphProperties properties) {
        this.props = properties;
        this.bullet = new XDDFParagraphBulletProperties(properties);
    }

    @Internal
    protected CTTextParagraphProperties getXmlObject() {
        return props;
    }

    public XDDFParagraphBulletProperties getBulletProperties() {
        return bullet;
    }

    public int getLevel() {
        if (props.isSetLvl()) {
            return 1 + props.getLvl();
        } else {
            return 0;
        }
    }

    public void setLevel(Integer level) {
        if (level == null) {
            props.unsetLvl();
        } else if (level < 1 || 9 < level) {
            throw new IllegalArgumentException("Minimum inclusive: 1. Maximum inclusive: 9.");
        } else {
            props.setLvl(level - 1);
        }
    }

    public XDDFRunProperties getDefaultRunProperties() {
        if (props.isSetDefRPr()) {
            return new XDDFRunProperties(props.getDefRPr());
        } else {
            return null;
        }
    }

    public void setDefaultRunProperties(XDDFRunProperties properties) {
        if (properties == null) {
            props.unsetDefRPr();
        } else {
            props.setDefRPr(properties.getXmlObject());
        }
    }

    public void setEastAsianLineBreak(Boolean value) {
        if (value == null) {
            props.unsetEaLnBrk();
        } else {
            props.setEaLnBrk(value);
        }
    }

    public void setLatinLineBreak(Boolean value) {
        if (value == null) {
            props.unsetLatinLnBrk();
        } else {
            props.setLatinLnBrk(value);
        }
    }

    public void setHangingPunctuation(Boolean value) {
        if (value == null) {
            props.unsetHangingPunct();
        } else {
            props.setHangingPunct(value);
        }
    }

    public void setRightToLeft(Boolean value) {
        if (value == null) {
            props.unsetRtl();
        } else {
            props.setRtl(value);
        }
    }

    public void setFontAlignment(FontAlignment align) {
        if (align == null) {
            props.unsetFontAlgn();
        } else {
            props.setFontAlgn(align.underlying);
        }
    }

    public void setTextAlignment(TextAlignment align) {
        if (align == null) {
            props.unsetAlgn();
        } else {
            props.setAlgn(align.underlying);
        }
    }

    public void setDefaultTabSize(Double points) {
        if (points == null) {
            props.unsetDefTabSz();
        } else {
            props.setDefTabSz(Units.toEMU(points));
        }
    }

    public void setIndentation(Double points) {
        if (points == null) {
            props.unsetIndent();
        } else if (points < -4032 || 4032 < points) {
            throw new IllegalArgumentException("Minimum inclusive = -4032. Maximum inclusive = 4032.");
        } else {
            props.setIndent(Units.toEMU(points));
        }
    }

    public void setMarginLeft(Double points) {
        if (points == null) {
            props.unsetMarL();
        } else if (points < 0 || 4032 < points) {
            throw new IllegalArgumentException("Minimum inclusive = 0. Maximum inclusive = 4032.");
        } else {
            props.setMarL(Units.toEMU(points));
        }
    }

    public void setMarginRight(Double points) {
        if (points == null) {
            props.unsetMarR();
        } else if (points < 0 || 4032 < points) {
            throw new IllegalArgumentException("Minimum inclusive = 0. Maximum inclusive = 4032.");
        } else {
            props.setMarR(Units.toEMU(points));
        }
    }

    public void setLineSpacing(XDDFSpacing spacing) {
        if (spacing == null) {
            props.unsetLnSpc();
        } else {
            props.setLnSpc(spacing.getXmlObject());
        }
    }

    public void setSpaceAfter(XDDFSpacing spacing) {
        if (spacing == null) {
            props.unsetSpcAft();
        } else {
            props.setSpcAft(spacing.getXmlObject());
        }
    }

    public void setSpaceBefore(XDDFSpacing spacing) {
        if (spacing == null) {
            props.unsetSpcBef();
        } else {
            props.setSpcBef(spacing.getXmlObject());
        }
    }

    public XDDFTabStop addTabStop() {
        if (!props.isSetTabLst()) {
            props.addNewTabLst();
        }
        return new XDDFTabStop(props.getTabLst().addNewTab());
    }

    public XDDFTabStop insertTabStop(int index) {
        if (!props.isSetTabLst()) {
            props.addNewTabLst();
        }
        return new XDDFTabStop(props.getTabLst().insertNewTab(index));
    }

    public void removeTabStop(int index) {
        if (props.isSetTabLst()) {
            props.getTabLst().removeTab(index);
        }
    }

    public XDDFTabStop getTabStop(int index) {
        if (props.isSetTabLst()) {
            return new XDDFTabStop(props.getTabLst().getTabArray(index));
        } else {
            return null;
        }
    }

    public List<XDDFTabStop> getTabStops() {
        if (props.isSetTabLst()) {
            return Collections.unmodifiableList(props
                .getTabLst()
                .getTabList()
                .stream()
                .map(gs -> new XDDFTabStop(gs))
                .collect(Collectors.toList()));
        } else {
            return Collections.emptyList();
        }
    }

    public int countTabStops() {
        if (props.isSetTabLst()) {
            return props.getTabLst().sizeOfTabArray();
        } else {
            return 0;
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
