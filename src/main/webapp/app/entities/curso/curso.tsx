import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICurso } from 'app/shared/model/curso.model';
import { getEntities } from './curso.reducer';

export const Curso = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const cursoList = useAppSelector(state => state.curso.entities);
  const loading = useAppSelector(state => state.curso.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="curso-heading" data-cy="CursoHeading">
        <Translate contentKey="labArquiJHipsterApp.curso.home.title">Cursos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="labArquiJHipsterApp.curso.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/curso/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="labArquiJHipsterApp.curso.home.createLabel">Create new Curso</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cursoList && cursoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.curso.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.curso.cursoId">Curso Id</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.curso.codigo">Codigo</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.curso.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="labArquiJHipsterApp.curso.credito">Credito</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cursoList.map((curso, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/curso/${curso.id}`} color="link" size="sm">
                      {curso.id}
                    </Button>
                  </td>
                  <td>{curso.cursoId}</td>
                  <td>{curso.codigo}</td>
                  <td>{curso.nombre}</td>
                  <td>{curso.credito}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/curso/${curso.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/curso/${curso.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/curso/${curso.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="labArquiJHipsterApp.curso.home.notFound">No Cursos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Curso;
