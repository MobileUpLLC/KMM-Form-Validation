package ru.mobileup.kmm_form_validation.options

enum class ImeAction {
    /**
     * Use the platform and keyboard defaults and let the keyboard to decide the action. The
     * keyboards will mostly show one of [Done] or [None] actions based on the single/multi
     * line configuration.
     */
    Default,

    /**
     * Represents that no action is expected from the keyboard.
     */
    None,

    /**
     * Represents that the user would like to go to the target of the text in the input i.e.
     * visiting a URL.
     */
    Go,

    /**
     * Represents that the user wants to execute a search, i.e. web search query.
     */
    Search,

    /**
     * Represents that the user wants to send the text in the input, i.e. an SMS.
     */
    Send,

    /**
     * Represents that the user wants to return to the previous input i.e. going back to the
     * previous field in a form.
     */
    Previous,

    /**
     * Represents that the user is done with the current input, and wants to move to the next
     * one i.e. moving to the next field in a form.
     */
    Next,

    /**
     * Represents that the user is done providing input to a group of inputs. Some
     * kind of finalization behavior should now take place i.e. the field was the last element in
     * a group and the data input is finalized.
     */
    Done
}