You can get OmegaT source from www.omegat.org or
https://github.com/omegat-org/omegat

We use OmegaT 4.0 development master snapshot as in 23, July, 2016
as git ID: fb7be6df31900c4166c1cdae7d04df683effe984
with 3 patches bundled here.

1. Use system proxy configuration for OmegaT
(0001-Use-OS-s-proxy-setting-when-running-on-Linux-and-Win.patch)

It is an ad-hoc workaround for users who is behind proxy server and
using git for team repository.

2. Leave unknown HTML entities as-is when generating translation files.
(0001-Filter-HTML-Leave-unknown-entities-as-is.patch)

It is useful for IntelliJ platform translation project which source
use special HTML entities.

3. Don't ask to convert an old team structure to new form when --no-team
   option specified even when running GUI.
(0001-Don-t-run-project-team-structure-when-no-team-CLI-op.patch)

It is useful when you are software developer and don't want to break
your git repository by OmegaT 4.0.
