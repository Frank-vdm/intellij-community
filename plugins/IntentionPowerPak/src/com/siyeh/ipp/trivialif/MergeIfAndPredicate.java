package com.siyeh.ipp.trivialif;

import com.intellij.psi.*;
import com.siyeh.ipp.base.PsiElementPredicate;
import com.siyeh.ipp.psiutils.ConditionalUtils;

class MergeIfAndPredicate implements PsiElementPredicate
{
    public boolean satisfiedBy(PsiElement element)
    {
        if(!(element instanceof PsiJavaToken))
        {
            return false;
        }
        final PsiJavaToken token = (PsiJavaToken) element;

        final PsiElement parent = token.getParent();
        if(!(parent instanceof PsiIfStatement))
        {
            return false;
        }
        final PsiIfStatement ifStatement = (PsiIfStatement) parent;
        PsiStatement thenBranch = ifStatement.getThenBranch();
        thenBranch = ConditionalUtils.stripBraces(thenBranch);
        PsiStatement elseBranch = ifStatement.getElseBranch();
        elseBranch = ConditionalUtils.stripBraces(elseBranch);
        if(thenBranch == null)
        {
            return false;
        }
        if(elseBranch != null)
        {
            return false;
        }
        if(!(thenBranch instanceof PsiIfStatement))
        {
            return false;
        }
        final PsiIfStatement childIfStatement = (PsiIfStatement) thenBranch;

        return childIfStatement.getElseBranch() == null;
    }
}
