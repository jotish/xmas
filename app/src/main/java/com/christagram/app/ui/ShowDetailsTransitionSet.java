package com.christagram.app.ui;

import android.content.Context;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.View;

import com.christagram.app.R;
import com.christagram.app.transitions.TextResizeTransition;
import com.christagram.app.transitions.TransitionBuilder;
import com.christagram.app.view.DetailsLayout;


public class ShowDetailsTransitionSet extends TransitionSet {
    private static final String TITLE_TEXT_VIEW_TRANSITION_NAME = "titleTextView";
    private static final String CARD_VIEW_TRANSITION_NAME = "cardView";
    private final String transitionName;
    private final View from;
    private final DetailsLayout to;
    private final Context context;

    public ShowDetailsTransitionSet(final Context ctx, final String transitionName,
        final View from, final DetailsLayout to) {
        context = ctx;
        this.transitionName = transitionName;
        this.from = from;
        this.to = to;
        addTransition(textResize());
        addTransition(slide());
        addTransition(shared());
    }

    private String titleTransitionName() {
        return transitionName + TITLE_TEXT_VIEW_TRANSITION_NAME;
    }

    private String cardViewTransitionName() {
        return transitionName + CARD_VIEW_TRANSITION_NAME;
    }

    private Transition textResize() {
        return new TransitionBuilder(new TextResizeTransition())
                .link(from.findViewById(R.id.item_name), to.titleView, titleTransitionName())
                .build();
    }

    private Transition slide() {
        return new TransitionBuilder(
            TransitionInflater.from(context)
                .inflateTransition(R.transition.enter_transition))
                .excludeTarget(transitionName, true)
                .excludeTarget(to.titleView, true)
                .excludeTarget(to.cardViewContainer, true)
                .build();
    }

    private Transition shared() {
        return new TransitionBuilder(TransitionInflater.from(context).inflateTransition(android.R.transition.move))
                .link(from.findViewById(R.id.photo), to.placeImageView, transitionName)
                .link(from, to.cardViewContainer, cardViewTransitionName())
                .build();
    }
}