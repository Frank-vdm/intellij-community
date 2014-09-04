/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.openapi.options;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * SearchableConfigurable instances would be instantiated on buildSearchableOptions step during Installer's build to index of all available options. 
 * {@link #com.intellij.ide.ui.search.TraverseUIStarter}
 */
public interface SearchableConfigurable extends Configurable {
  @NotNull
  @NonNls String getId();

  @Nullable Runnable enableSearch(String option);

  interface Parent extends SearchableConfigurable, Composite {
    boolean hasOwnContent();

    /**
     * @deprecated use {@link ConfigurableProvider#canCreateConfigurable()} instead
     *             to specify configurables which should not be visible
     */
    @Deprecated
    boolean isVisible();

    abstract class Abstract implements Parent {
      private Configurable[] myKids;

      @Override
      public JComponent createComponent() {
        return null;
      }

      @Override
      public boolean hasOwnContent() {
        return false;
      }

      
      @Override
      public boolean isModified() {
        return false;
      }

      @Override
      public void apply() throws ConfigurationException {
      }

      @Override
      public void reset() {
      }

      @Override
      public void disposeUIResources() {
        myKids = null;
      }

      @Override
      public Runnable enableSearch(final String option) {
        return null;
      }

      @Override
      public boolean isVisible() {
        return true;
      }

      @Override
      public final Configurable[] getConfigurables() {
        if (myKids != null) return myKids;
        myKids = buildConfigurables();
        return myKids;
      }

      protected abstract Configurable[] buildConfigurables();
    }
  }
}
