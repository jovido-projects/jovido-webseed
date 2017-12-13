package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.FragmentService;

/**
 * @author Stephan Grundner
 */
public class DependentFragmentEditor extends FragmentEditor {

    private PayloadField originField;

    public DependentFragmentEditor(FragmentService fragmentService) {
        super(fragmentService);
    }
}
