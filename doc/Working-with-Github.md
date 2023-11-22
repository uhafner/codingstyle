Disclaimer: The following guide was created with significant help from the two tutorials listed below:

- [How To Create a Pull Request on GitHub](https://www.digitalocean.com/community/tutorials/how-to-create-a-pull-request-on-github)
- [GitHub Standard Fork & Pull Request Workflow](https://gist.github.com/Chaser324/ce0505fbed06b947d962)

# Working with Pull Requests in GitHub
Submissions in GitHub are made through pull requests. The steps described in the following sections are necessary for this process.

### Creating a Fork
To submit a pull request with your results, you first need to create a [fork](https://help.github.com/en/github/getting-started-with-github/fork-a-repo) of the respective project. This cannot be done through the command line but only in the GitHub interface, as outlined [in the guide](https://help.github.com/en/github/getting-started-with-github/fork-a-repo#fork-an-example-repository). This newly created fork is initially an exact copy of the original project, meaning it contains all commits, branches, and tags.

### Working with the Fork
Once the fork is created, it becomes visible under your own GitHub user account as a copy. This copy can be retrieved to your local machine using the following command:

```bash
# Clone your fork to your local machine using SSH
git clone git@github.com:USERNAME/FORKED-PROJECT.git
```

If an SSH key is not yet set up on GitHub, this can alternatively be done using HTTPS:

```bash
# Clone your fork to your local machine using HTTPS
git clone https://github.com/USERNAME/FORKED-PROJECT.git
```

For easy password-free use of GitHub, I recommend setting up SSH as quickly as possible.

### Developing Your Own Changes
For each submission (and for every small change to the project), a new branch must be created. Working on the `master` branch is not advisable, as discussed in the "Keeping Fork Updated" chapter.

To create a branch, the following steps are necessary:

```bash
# Checkout the master branch - you want your new branch to come from master
git checkout master

# Create a new branch named newfeature (give your branch its own simple informative name)
git branch newfeature


# Switch to your new branch
git checkout newfeature
```

Now it's time to program, and all changes are made step by step. Test Driven Development has proven effective here, but that is not part of this guide (see the Testing chapter in my coding guidelines).

Another useful approach is incremental development: development is not done in one go and then completed with a commit, but in several iterations. Each step that can be translated without errors and passes all tests afterward should be individually completed with a commit. This makes it easier to understand the changes afterward.

When committing, it is important to give a good commit message. Chris Beam has written a helpful article on this, titled ["How to Write a Git Commit Message."](https://chris.beams.io/posts/git-commit/)

# Prepare and Submit a Pull Request
Once all changes have been locally completed with a commit, they can be integrated into the fork on GitHub. Only a push is required for this:

```bash
# Push the local branch newfeature to a remote branch in the forked repository (using the same name) 
git push --set-upstream origin newfeature
```

Now these changes are also visible online in your GitHub project. GitHub automatically recognizes that a new branch has been created and offers a corresponding button in the interface. Alternatively, a new pull request can be created through the [Pull Request dialog](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request-from-a-fork).

When creating the pull request, a title and description must be entered. The title should contain the task's name, and the description may include additional details. The use of salutation, closing, or closing formulas is not meaningful.

Before finally creating the pull request, it must be checked whether the pull request contains the desired changes - and only those! Open the Files section in the dialog and go through each change. If changes unrelated to the submission appear there, such as formatting changes or blank line changes in unrelated sections or completely different files, they should be removed. To integrate such changes into the pull request, they must be implemented using the workflow described above: make changes to the corresponding files in the editor, commit locally, and then push to the GitHub project.

If the pull request looks as desired, it can be created with "Create."

# Update the Pull Request
Once the pull request is created, it is automatically checked with various tools. The tools used depend on the project. Typically, [continuous integration](Continuous-Integration.md) is started, executing a development lifecycle:

1. Compile
2. Test
3. Analyze

Each of these steps is marked in GitHub with an Ok or Failed status. If any of the steps are marked as Failed, the pull request needs to be revised. To do this, the error must be analyzed, and then the source code needs to be updated in the appropriate places, whether it's compile or test errors, falling below the required test coverage, or violations of coding guidelines.

If all automatic tests are Ok, only the review by the author of the original project is missing. This is also done line by line in the pull request and can be incorporated with the same steps as described above. GitHub usually detects these changes automatically, so they do not need to be explicitly marked as resolved.

# Keeping the Fork Updated
It is important to keep this fork up to date, i.e., always sync the changes from the original project. In most cases, it is sufficient to keep the so-called master branch synchronized. To enable this, the original project must be added as another remote. The name "upstream" has become common for this. This can be implemented with the following command:

```bash
# Add 'upstream' repo to list of remotes
git remote add upstream https://github.com/UPSTREAM-USER/ORIGINAL-PROJECT.git
```

The following command can be used to check the configuration:
```bash
# Verify the new remote named 'upstream'
git remote -v
```

The output should then be as follows:

```bash
origin  git@github.com:USERNAME/FORKED-PROJECT.git (fetch)
origin  git@github.com:USERNAME/FORKED-PROJECT.git (push)
upstream  https://github.com/UPSTREAM-USER/ORIGINAL-PROJECT.git (fetch)
upstream  https://github.com/UPSTREAM-USER/ORIGINAL-PROJECT.git (push)
```
Whenever the changes from the master branch of the original project need to be integrated, they can be incorporated with the following command:

```bash
# Fetch from upstream remote
git fetch upstream

# View all branches, including those from upstream
git branch -va

# Checkout your master branch and merge upstream
git checkout master
git merge upstream/master
```
Normally, there should be no other commits on the local master branch, so a [fast-forward](https://git-scm.com/book/de/v2/Git-Branching-Einfaches-Branching-und-Merging) will be applied.
