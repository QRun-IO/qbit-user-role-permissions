# qbit-user-role-permissions

## Knowledge base

Deep-review dossiers for this repo and the wider QQQ platform live in the
second-brain vault:

- Hub / map of the QQQ knowledge base:
  `R:/Git.Local/KofTwentyTwo/second-brain/knowledge/qqq/qqq-hub.md`
- This repo's dossier (purpose, API surface, QBit contract usage, data model,
  build/release, risks, v4.0 impact):
  `R:/Git.Local/KofTwentyTwo/second-brain/knowledge/qqq/repos/qbit-user-role-permissions.md`

Reviewed at branch `develop`, commit `860cabe6c1bd` (2026-05-21).

Notable at that commit (see dossier for detail): develop has diverged behind
`main` (main carries v0.32.0, Java 21, Apache-2.0 LICENSE; develop is still
0.30.0, Java 17 via qbit-build-parent 1.4.0, AGPL); qqq coupling is
qqq-bom-pom 0.27.9 via the parent — a parent bump is required before building
against qqq 4.0.
