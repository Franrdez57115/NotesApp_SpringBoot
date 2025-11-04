const modal = document.getElementById('noteModal');
const addNoteBtn = document.getElementById('addNoteBtn');
const closeModalBtn = document.getElementById('closeModalBtn');
const cancelBtn = document.getElementById('cancelBtn');
const saveNoteBtn = document.getElementById('saveNoteBtn');

const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

function openModal() {
    modal.classList.remove('hidden');
    modal.classList.add('flex');
}

function closeModal() {
    modal.classList.add('hidden');
    modal.classList.remove('flex');
}

addNoteBtn.addEventListener('click', () => {
    document.getElementById('modalTitle').textContent = 'Nueva Nota';
    document.getElementById('noteId').value = '';
    document.getElementById('noteTitle').value = '';
    document.getElementById('noteDescription').value = '';
    document.getElementById('noteAutor').value = '';
    document.getElementById('noteState').value = 'CREADA';
    document.getElementById('noteColor').value = '#ffffff';
    openModal();
});

closeModalBtn.addEventListener('click', closeModal);
cancelBtn.addEventListener('click', closeModal);

saveNoteBtn.addEventListener('click', async () => {
    const id = document.getElementById('noteId').value;
    const title = document.getElementById('noteTitle').value.trim();

    if (!title) {
        alert('El título es obligatorio.');
        return;
    }

    const note = {
        title,
        description: document.getElementById('noteDescription').value,
        autor: document.getElementById('noteAutor').value,
        state: document.getElementById('noteState').value,
        color: document.getElementById('noteColor').value
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `/notes/${id}` : `/notes/new`;

    const res = await fetch(url, {
        method,
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify(note)
    });

    if (res.ok) {
        location.reload();
    } else {
        alert('Error al guardar la nota');
    }
});


function openEditModal(button) {
    document.getElementById('modalTitle').textContent = 'Editar Nota';
    document.getElementById('noteId').value = button.dataset.id;
    document.getElementById('noteTitle').value = button.dataset.title;
    document.getElementById('noteDescription').value = button.dataset.description;
    document.getElementById('noteAutor').value = button.dataset.autor;
    document.getElementById('noteState').value = button.dataset.state;
    document.getElementById('noteColor').value = button.dataset.color;
    openModal();
}

async function deleteNote(id) {
    const modal = document.getElementById('deleteModal');
    const confirmBtn = document.getElementById('confirmDelete');
    const cancelBtn = document.getElementById('cancelDelete');

    modal.classList.remove('hidden');

    const confirmed = await new Promise(resolve => {
        const handleConfirm = () => {
            cleanup();
            resolve(true);
        };
        const handleCancel = () => {
            cleanup();
            resolve(false);
        };
        const cleanup = () => {
            confirmBtn.removeEventListener('click', handleConfirm);
            cancelBtn.removeEventListener('click', handleCancel);
            modal.classList.add('hidden');
        };

        confirmBtn.addEventListener('click', handleConfirm);
        cancelBtn.addEventListener('click', handleCancel);
    });

    if (!confirmed) return;

    const res = await fetch(`/notes/${id}`, {
        method: 'DELETE',
        headers: { [csrfHeader]: csrfToken }
    });

    if (res.ok) location.reload();
}


























