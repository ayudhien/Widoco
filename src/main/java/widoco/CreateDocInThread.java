/*
 * Copyright 2012-2013 Ontology Engineering Group, Universidad Politécnica de Madrid, Spain
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package widoco;

import java.io.File;
import widoco.gui.GuiController;

/**
 *
 * @author Daniel Garijo
 */
public class CreateDocInThread implements Runnable{
    private final Configuration c;
    private final GuiController pointerToMain;
    private final File tmpFile;
    
    public CreateDocInThread(Configuration c, GuiController g, File lodeTmpResources){
        this.c = c;
        this.pointerToMain = g;
        this.tmpFile = lodeTmpResources;
    }

    public void run() {
        //el false/true es por el path de la doc o la uri. Aclararse..
        //use config to create doc. If null, return error.
        try{
            System.out.println("Generating doc. "+ c.getMainOntology().getNamespaceURI());
            CreateResources.generateDocumentation(c.getDocumentationURI(), c, tmpFile);
            this.pointerToMain.switchState("next");
        }catch(Exception e){
            System.err.println("Error while generating the documentation " +e.getMessage());
            c.setError("An error occurred while generating the documentation. Please that the ontology opens with Protege and that there are not empty metadata fields");
            this.pointerToMain.switchState("error");
        }        
    }

}
