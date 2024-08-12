 document.addEventListener("DOMContentLoaded", function () {
                // Check if there's any flash message to show
                const flashMessage = document.body.getAttribute('data-flash-message');
                const flashError = document.body.getAttribute('data-flash-error');


                if (flashMessage) {
                    toastr.success(flashMessage);
                }
                if (flashError) {
                    toastr.error(flashError);
                }

 });